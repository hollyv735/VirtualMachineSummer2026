package com.kondra.vm.vmx;

import com.kondra.vm.common.Version;
import com.kondra.vm.common.vmx.VmxExt;
import com.kondra.vm.common.vmx.VmxFile;
import com.kondra.vm.common.vmx.ext.Export;
import com.kondra.vm.common.vmx.ext.Relocation;
import com.kondra.vm.vmx.ext.*;
import com.kondra.vm.vmx.readers.VmxReader;
import com.kondra.vm.vmx.writers.VmxWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VmxUtil {

    private static String typeString(int i){
        switch(i){
            case(1):
                return "Relocation";
            case(2):
                return "Symbol Table";
            case(3):
                return "Preload";
            case(4):
                return "Export";
            case(5):
                return "Debug";
            case(6):
                return "Label";
            case(7):
                return "Affinity";
        }
        return"";

    }

    public static void detail(VmxFile file){
        List<VmxExt> extensions = file.getExtensions();
        int numExt = extensions.size();
        System.out.println("========================================");
        System.out.println("Detailed Information: ");
        System.out.println("extensions: " + numExt);
        int i = 1;
        for (VmxExt e : extensions){
            System.out.println("("+i+") "+ typeString(e.getType()));
            i++;
        }
        System.out.println("========================================");

    }

    public static void info(VmxFile file){
        Version version = file.getVersion();
        int buildNum = file.getVersion().getBuildNum();
        System.out.println("========================================");
        System.out.println("Version information:");
        System.out.println("Version: " + version.getMajor()+"."+version.getMinor());
        System.out.println("Build Number: " + buildNum);
        //check for label extension
        List<VmxExt> extensions = file.getExtensions();
        for(VmxExt e: extensions) {
            if (e.getType() == VmxExt.TYPE_LABEL){
                System.out.println("Label Extension:");
                System.out.println("Timestamp:" + ((MyLabelExt)e).getTimestamp());
                System.out.println("Label:" + ((MyLabelExt)e).getLabel());
            }
        }
        System.out.println("========================================");
    }

    public static void preload(VmxFile file){
        System.out.println("========================================");
        List<VmxExt> extensions = file.getExtensions();
        System.out.println("required preloads:");
        List<Integer> offsets = null;
        MySymbolTableExt symTab = null;
        for(VmxExt e: extensions) {
            if (e.getType() == VmxExt.TYPE_PRELOAD){
                offsets = ((MyPreloadExt)e).getSymbolOffsets();
            }
            if (e.getType() == VmxExt.TYPE_SYMTAB){
                symTab = (MySymbolTableExt) e;
            }
        }
        for (Integer o: offsets){
            System.out.println(symTab.getSymbol(o));
        }
        System.out.println("========================================");
    }
    public static void importSymbols(VmxFile file){
        System.out.println("========================================");
        System.out.println("imported symbols: ");
        List<VmxExt> extensions = file.getExtensions();
        List<Relocation> relocations = null;
        List<Relocation> dynamicRelocations = new ArrayList<>();
        MySymbolTableExt symTab = null;

        for(VmxExt e: extensions) {
            if (e.getType() == VmxExt.TYPE_RELOC) {
                for(int i = 0; i<4; i++) {
                    relocations = ((MyRelocationExt) e).getRelocations(i);
                    for (Relocation r : relocations) {
                        if (r.isDynamic()) {
                            dynamicRelocations.add(r);
                        }
                    }
                }
            }
            if (e.getType() == VmxExt.TYPE_SYMTAB){
                symTab = (MySymbolTableExt) e;
            }
        }
        List<String> print = new ArrayList<>();
        for (Relocation r : dynamicRelocations){
            String s = symTab.getSymbol(r.getDynamicSymbolOffset());
            if (!print.contains(s)){
                print.add(s);
            }
        }
        for(String s : print){
            System.out.println(s);
        }

    }
    public static void exportSymbols(VmxFile file){
        System.out.println("========================================");
        System.out.println("exported symbols: ");
        List<VmxExt> extensions = file.getExtensions();
        List<Export> exports = null;
        MySymbolTableExt symTab = null;
        for(VmxExt e: extensions) {
            if (e.getType() == VmxExt.TYPE_EXPORT) {
                exports = ((MyExportExt)e).getExports();
            }
            if (e.getType() == VmxExt.TYPE_SYMTAB){
                symTab = (MySymbolTableExt) e;
            }
        }
        for (Export e : exports){
            System.out.println(symTab.getSymbol(e.getSymbolOffset()));
        }
        System.out.println("========================================");
    }
    //parameters required
    public static void output(VmxFile file, String outputFile){
        VmxWriter writer = new VmxWriter((MyVmxFile) file);
        File pathway = new File(outputFile);
        writer.write(pathway);
        //System.out.println("file saved to " + outputFile);
    }
    public static void version(VmxFile file, String version){ //finish
        //System.out.println("setting version to " + version);
        String[] majorMinor = version.split("\\.");
        file.getVersion().setMajor(Integer.parseInt(majorMinor[0]));
        file.getVersion().setMajor(Integer.parseInt(majorMinor[1]));


    }
    public static void build(VmxFile file, String num){
        try {
            //System.out.println("setting build num to " + num);
            file.getVersion().setBuildNum(Integer.parseInt(num));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
    public static void label(VmxFile file, String str){
        //System.out.println("setting label to " + str);
        List<VmxExt> extensions = file.getExtensions();
        for(VmxExt e: extensions) {
            if (e.getType() == VmxExt.TYPE_LABEL){
                ((MyLabelExt)e).setLabel(str);
                ((MyLabelExt)e).setTimestamp(new Date());
            }
        }
    }
    public static void help(VmxFile file){
        System.out.println("========================================\n" +
                "usage: VmxUtil [options]\n" +
                " -build <arg>   Set the build number.\n" +
                " -detail        Display information about vmx internals.\n" +
                " -export        Display the exported symbols.\n" +
                " -import        Display the imported symbols.\n" +
                " -info          Display version and label for the vmx file.\n" +
                " -label <arg>   Set the label string (up to 32 chars).\n" +
                " -o <arg>       If modifying the file, output to the specified file.\n" +
                " -preload       Display the list of preloads for this vmx.\n" +
                " -vers <arg>    Set the major and minor version using N.n format.\n" +
                "========================================");
    }




    public static void main(String[] args){
        File file;
        if(args.length<1){
            throw new RuntimeException("no arguments provided");
        }
        boolean info = false;
        boolean detail = false;
        boolean preload = false;
        boolean h = false;
        boolean importSymbol = false;
        boolean exportSymbol = false;
        //parameters required
        String output = null;
        String version = null;
        String build = null;
        String label = null;
        for (int i = 0; i<args.length-1; i++){
            if (args[i].contains("info")){
                info = true;
            }
            else if (args[i].contains("detail")){
                detail = true;
            }
            else if (args[i].contains("preload")){
                preload = true;
            }
            else if (args[i].equals("h")){
                h = true;
            }
            else if(args[i].contains("import")){
                importSymbol = true;
            }
            else if(args[i].contains("export")){
                exportSymbol = true;
            }
            else if(args[i].contains("vers")){
                if(args.length==i+1){
                    throw new RuntimeException("no parameter listed");
                }
                version = args[++i];

            }
            else if(args[i].equals("-o")){
                if(args.length==i+1){
                    throw new RuntimeException("no parameter listed");
                }
                output = args[++i];
            }
            else if(args[i].contains("build")){
                if(args.length==i+1){
                    throw new RuntimeException("no parameter listed");
                }
                build = args[++i];
            }
            else if(args[i].contains("label")){
                if(args.length==i+1){
                    throw new RuntimeException("no parameter listed");
                }
                label = args[++i];
            }
            else {
                System.out.println(args[i]);
                throw new RuntimeException("string not recognized");
            }
        }
        String pathway = args[args.length-1];
        file = new File(pathway);
        VmxReader reader = new VmxReader(file);
        VmxFile vmxFile = reader.getVmxFile();
        if (info){
            info(vmxFile);
        }
        if (detail){
            detail(vmxFile);
        }
        if (preload){
            preload(vmxFile);
        }
        if(h){
            help(vmxFile);
        }
        if(importSymbol){
            importSymbols(vmxFile);
        }
        if(exportSymbol){
            exportSymbols(vmxFile);
        }
        if (output != null){
            if (label != null){
                label(vmxFile, label);
            }
            if (build != null){
                build(vmxFile, build);
            }
            if (version != null){
                version(vmxFile, version);
            }
            output(vmxFile, output);
        } else {
            if (build != null){
                build(vmxFile, build);
            }
            if (label != null){
                label(vmxFile, label);
            }
            if (version != null){
                version(vmxFile, version);
            }
            VmxWriter writer = new VmxWriter((MyVmxFile) vmxFile);
            writer.write(file);
        }
    }
}


