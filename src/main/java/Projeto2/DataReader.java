package Projeto2;

import Projeto2.Entities.Cidadao;
import Projeto2.Entities.Supermercado;

import java.io.*;
import java.util.ArrayList;

public class DataReader {

    String PATH_TO_FILE = "Projeto2/file_tests/";

    private int avenidas;
    private int ruas;

    private int numSupermercados;
    private int numCidadaos;

    private ArrayList<Supermercado> supermercados;
    private ArrayList<Cidadao> cidadaos;

    public DataReader(String fileName, ArrayList<Supermercado> supermercados, ArrayList<Cidadao> cidadaos) throws IOException {
        this.supermercados = supermercados;

        this.cidadaos = cidadaos;

        readFile(fileName);
    }

    private void readFile(String fileName) throws IOException {
        try{
            File file = new File(PATH_TO_FILE + fileName);

            //System.out.println("Caminho absoluto: " + file.getAbsolutePath());

            BufferedReader br = new BufferedReader(new FileReader(file));

            // Número de avenidas e ruas
            String[] line = br.readLine().split(" ");
            avenidas = Integer.parseInt(line[0]);
            ruas = Integer.parseInt(line[1]);

            // Número de supermercados e cidadãos
            line = br.readLine().split(" ");
            numSupermercados = Integer.parseInt(line[0]);
            numCidadaos = Integer.parseInt(line[1]);

            readSupermercados(br);
            readCidadaos(br);

        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readCidadaos(BufferedReader br) throws IOException {
        for (int i = 0; i < numCidadaos; i++) {
            String[] line = br.readLine().split(" ");

            int avenida = Integer.parseInt(line[0]);
            int rua = Integer.parseInt(line[1]);

            cidadaos.add(new Cidadao(avenida, rua));
        }
    }

    private void readSupermercados(BufferedReader br) throws IOException {
        for (int i = 0; i < numSupermercados; i++) {
            String[] line = br.readLine().split(" ");

            int avenida = Integer.parseInt(line[0]);
            int rua = Integer.parseInt(line[1]);

            supermercados.add(new Supermercado(avenida, rua));
        }
    }

    public ArrayList<Supermercado> getSupermercados() {
        return supermercados;
    }

    public ArrayList<Cidadao> getCidadaos() {
        return cidadaos;
    }
}
