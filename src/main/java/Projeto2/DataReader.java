package Projeto2;

import Projeto2.Entities.Cidadao;
import Projeto2.Entities.Supermercado;

import java.io.*;
import java.util.ArrayList;

public class DataReader {

    private String PATH_TO_FILE = "file_tests/";

    private int avenidas;
    private int ruas;

    private int numSupermercados;
    private int numCidadaos;

    private ArrayList<Supermercado> supermercados;
    private ArrayList<Cidadao> cidadaos;

    public DataReader(String fileName, ArrayList<Supermercado> supermercados, ArrayList<Cidadao> cidadaos) {
        this.supermercados = supermercados;

        this.cidadaos = cidadaos;

        readFile(fileName);
    }

    private void readFile(String fileName) {
        try{
            File file = new File(PATH_TO_FILE + fileName);

            // System.out.println("Reading file: " + PATH_TO_FILE);

            // System.out.println("Caminho absoluto: " + file.getAbsolutePath());

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
        catch(NullPointerException e1) {
            throw new RuntimeException("ERRO: Ficheiro não encontrado.");
        }
        catch(IOException e2) {
            throw new RuntimeException("ERRO: Não é possível ler o ficheiro.");
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

    private void readSupermercados(BufferedReader br) {
        for (int i = 0; i < numSupermercados; i++) {
            String[] line = null;

            try {
                line = br.readLine().split(" ");
            } catch (IOException e) {
                throw new RuntimeException("ERRO: Não é possível ler a linha do ficheiro!");
            }

            int avenida = Integer.parseInt(line[0]);
            int rua = Integer.parseInt(line[1]);

            supermercados.add(new Supermercado(avenida, rua));
        }
    }

    public Supermercado getSupermercadoByCoords(int avenida, int rua) {
        return supermercados.stream()
                .filter(supermercado -> supermercado.getAvenida() == avenida && supermercado.getRua() == rua)
                .findFirst()
                .orElse(null);
    }

    public Cidadao getCidadaoByCoords(int avenida, int rua) {
        return cidadaos.stream()
                .filter(cidadao -> cidadao.getAvenida() == avenida && cidadao.getRua() == rua)
                .findFirst()
                .orElse(null);
    }

    public int getAvenidas() {
        return avenidas;
    }

    public int getRuas() {
        return ruas;
    }

    public int getNumSupermercados() {
        return numSupermercados;
    }

    public int getNumCidadaos() {
        return numCidadaos;
    }
}
