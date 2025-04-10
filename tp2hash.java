

import java.io.RandomAccessFile;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;

class tp2hash{
    
    final int TAM_CABECALHO = 4; 
    HashExtensivel<ParIDEndereco> indiceDireto;
    

   static class Pokemon implements RegistroHashExtensivel<Pokemon>{

    private int id;
    private String name;
    private List<String> types = new ArrayList<String>();
    private List<String> abilities = new ArrayList<String>();
    private int generation;
    private String description;
    private float weight;
    private float height;
    private int captureRate;
    private boolean isLegendary;
    private LocalDate captureDate;


     public Pokemon() {
        id = 0;
        name = "";
        types = new ArrayList<String>();
        abilities = new ArrayList<String>();
        generation = 0;
        description = "";
        weight = 0;
        height = 0;
        captureRate = 0;
        isLegendary = false;
        
      
    }
    //Contrutor do Pokemon.
    public Pokemon(int id, String name, ArrayList<String> types, ArrayList<String> abilities, int generation,
    String description, float weight, float height, int captureRate, boolean isLegendary, LocalDate captureDate){

        this.id = id;
        this.name = name;
        this.types = new ArrayList<>(types);
        this.abilities = new ArrayList<>(abilities);
        this.generation = generation;
        this.description = description;
        this.weight = weight;
        this.height = height;
        this.captureRate = captureRate;
        this.isLegendary = isLegendary;
        this.captureDate = captureDate ;
       
    }

    public int hashCode() {
      return this.id; // Chave é o ID
  }

    public short size() { // Retorna short
      try {
          return (short) this.toByteArray().length; // Conversão para short
      } catch (IOException e) {
          return 0;
      }
  }

    public void setId(int id) {
        this.id = id;
    }

     public void setName(String name) {
        if (name.equals(" ")) {
            name = "";
        }
        this.name = name;
    }

   
    public void addType(String type) {
        types.add("'" + type + "'");  // Adiciona o tipo com aspas simples
    }

    public void addAbility(String ability) {
        abilities.add("'" + ability + "'");  // Adiciona a habilidade com aspas simples
    }

    public void setTypes(List<String> newTypes) {
        this.types = new ArrayList<>(newTypes);
    }
    
    public void setAbilities(List<String> newAbilities) {
        this.abilities = new ArrayList<>(newAbilities);
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public void setDescription(String description) {
        if (description.equals(" ")) {
            description = "";
        }
        this.description = description;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setCaptureRate(int captureRate) {
        this.captureRate = captureRate;
    }

    public void setIsLegendary(boolean isLegendary) {
        this.isLegendary = isLegendary;
    }


    public void setCaptureDate(LocalDate date) {
        this.captureDate = date;
    }

    private LocalDate parseDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateStr, formatter);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getTypes() {
        return types;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public int getGeneration() {
        return generation;
    }

    public String getDescription() {
        return description;
    }

    public float getWeight() {
        return weight;
    }

    public float getHeight() {
        return height;
    }

    public int getCaptureRate() {
        return captureRate;
    }

    public boolean getIsLegendary() {
        return isLegendary;
    }

    public LocalDate getCaptureDate() {
        return captureDate;
    }

        //Converter para string.
        public String toString() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = (captureDate != null) ? captureDate.format(formatter) : "N/A";
            return "\nID.............: " + this.id +
                   "\nNome...........: " + this.name +
                   "\nTipos..........: " + this.types +
                   "\nAbilidades.....: " + this.abilities +
                   "\nGeração........: " + this.generation +
                   "\nDescrção.......: " + this.description +
                   "\nPeso...........: " + this.weight +
                   "\nAltura.........: " + this.height +
                   "\nTaxa de captura: " + this.captureRate +
                   "\nÉ lendario?....: " + this.isLegendary +
                   "\nNascimento.....: " + formattedDate;
    
    }
 
    //id,generation,name,description,type1,type2,abilities,weight_kg,height_m,capture_rate,is_legendary,capture_date
    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
    
        dos.writeInt(this.id);
        dos.writeUTF(this.name);
    
        // Serializando a lista de tipos
        dos.writeInt(this.types.size());
        for (String type : this.types) {
            dos.writeUTF(type);
        }
    
        // Serializando a lista de habilidades
        dos.writeInt(this.abilities.size());
        for (String ability : this.abilities) {
            dos.writeUTF(ability);
        }
    
        dos.writeInt(this.generation);
        dos.writeUTF(this.description);
        dos.writeFloat(this.weight);
        dos.writeFloat(this.height);
        dos.writeInt(this.captureRate);
        dos.writeBoolean(this.isLegendary);
        dos.writeInt((int) this.captureDate.toEpochDay());
    
        return baos.toByteArray();
    }

    
    public void fromByteArray(byte[] data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);
    
        this.id = dis.readInt();
        this.name = dis.readUTF();
    
        // Desserializando a lista de tipos
        int typesSize = dis.readInt();
        this.types = new ArrayList<>();
        for (int i = 0; i < typesSize; i++) {
            this.types.add(dis.readUTF());
        }
    
        // Desserializando a lista de habilidades
        int abilitiesSize = dis.readInt();
        this.abilities = new ArrayList<>();
        for (int i = 0; i < abilitiesSize; i++) {
            this.abilities.add(dis.readUTF());
        }
    
        this.generation = dis.readInt();
        this.description = dis.readUTF();
        this.weight = dis.readFloat();
        this.height = dis.readFloat();
        this.captureRate = dis.readInt();
        this.isLegendary = dis.readBoolean();
        this.captureDate = LocalDate.ofEpochDay(dis.readInt());
    }

 }

 public static void importarCSVParaBD() {
    List<Pokemon> pokedex = new ArrayList<>();
    
    try (BufferedReader reader = new BufferedReader(new FileReader("pokemon.csv"))) {
        String line = reader.readLine(); // Lê o cabeçalho e ignora
        while ((line = reader.readLine()) != null) { 
            String[] attributes = line.split(",");
            int startPointer = 0;
            int endPointer = 0;
            
            for (int i = 0; i < attributes.length; i++) {
                for (int j = 0; j < attributes[i].length(); j++) {
                    if (attributes[i].charAt(j) == '[') {
                        startPointer = i;
                    } else if (attributes[i].charAt(j) == ']') {
                        endPointer = i;
                    }
                }
            }
            
            line = line.replaceAll("[\"'\\[\\]]", "").trim();
            attributes = line.split(",");
            
            Pokemon p = new Pokemon();
            int index = 0;
            try {
                p.setId(Integer.parseInt(attributes[index].trim()));
                index++;
                p.setGeneration(Integer.parseInt(attributes[index].trim()));
                index++;
                p.setName(attributes[index].trim());
                index++;
                p.setDescription(attributes[index].trim());
                index++;
                p.addType(attributes[index].trim());
                index++;
                
                if (!attributes[index].isEmpty()) {
                    p.addType(attributes[index].trim());
                }
                index++;
                
                int abilityCount = (endPointer - startPointer) + 1;
                
                for (int i = 6; i <= endPointer; i++) {
                    p.addAbility(attributes[i].trim());
                }
                
                index += abilityCount;
                
                if (!attributes[index].isEmpty()) {
                    p.setWeight(Float.parseFloat(attributes[index].trim()));
                    index++;
                } else {
                    p.setWeight(0);
                    index++;
                }
                
                if (!attributes[index].isEmpty()) {
                    p.setHeight(Float.parseFloat(attributes[index].trim()));
                    index++;
                } else {
                    p.setHeight(0);
                    index++;
                }
                
                if (!attributes[index].isEmpty()) {
                    p.setCaptureRate(Integer.parseInt(attributes[index].trim()));
                    index++;
                } else {
                    p.setCaptureRate(0);
                    index++;
                }
                
                p.setIsLegendary(attributes[index].equals("1"));
                index++;
                
                if (!attributes[index].isEmpty()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate date = LocalDate.parse(attributes[index].trim(), formatter);
                    p.setCaptureDate(date);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                continue; // Se der erro, pula para o próximo registro
            }
            
            pokedex.add(p);
               try{
                create(p); // Chama a função para salvar no banco de dados
               }
                catch(Exception e){
                    System.out.println("Erro ao criar.");
                    e.printStackTrace();
                } 
            
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

//Verifico se o CSV ja foi carregado para o meu .db
public static boolean Carregado() throws Exception {
    RandomAccessFile arquivo = new RandomAccessFile("pokemons.db", "rw");

    // Verifica se o arquivo está vazio
    if (arquivo.length() == 0) {
        return false; // Retorna false, indicando que o arquivo ainda não foi carregado
    }

    // Caso contrário, tenta ler o último ID
    arquivo.seek(0);
    int lastId = arquivo.readInt(); // Lê o último ID armazenado

    return lastId > 0; // Retorna true se houver IDs válidos (indica que o CSV foi carregado)
}



public class OrdenacaoExterna {
    private static final int TAM_MEMORIA = 5; // Tamanho do buffer de memória (quantidade de Pokemons que podemos carregar na memória de uma vez)
    private static final String ARQUIVO_ORIGINAL = "pokemons.db"; // Caminho do arquivo original que será ordenado
    private static final String TEMP1 = "temp1.db"; // Arquivo temporário 1 para armazenar dados parciais ordenados
    private static final String TEMP2 = "temp2.db"; // Arquivo temporário 2 para armazenar dados parciais ordenados

    public static void main(String[] args) throws Exception {
        ordenarArquivo(); // Chama o método para ordenar o arquivo
    }

    // Método principal para ordenar o arquivo
    public static void ordenarArquivo() throws Exception {
        dividirArquivo(); // Passo 1: Dividir o arquivo em partes menores
        intercalarArquivos(); // Passo 2: Intercalar os arquivos temporários ordenados em um único arquivo final
    }

    // Método para dividir o arquivo original em partes menores e ordená-las
    private static void dividirArquivo() throws Exception {
        RandomAccessFile arquivo = new RandomAccessFile(ARQUIVO_ORIGINAL, "r"); // Abre o arquivo original para leitura
        RandomAccessFile temp1 = new RandomAccessFile(TEMP1, "rw"); // Abre o arquivo temporário 1 para escrita
        RandomAccessFile temp2 = new RandomAccessFile(TEMP2, "rw"); // Abre o arquivo temporário 2 para escrita

        arquivo.seek(4); // Pula o cabeçalho (que contém o último ID armazenado no arquivo)
        boolean alternador = true; // Variável para alternar entre os arquivos temporários (temp1 e temp2)
        List<Pokemon> buffer = new ArrayList<>(); // Buffer para armazenar Pokemons temporariamente

        // Loop para ler e processar todos os dados do arquivo original
        while (arquivo.getFilePointer() < arquivo.length()) {
            // Verifica se o buffer atingiu o tamanho máximo, e caso tenha atingido, ordena e escreve os dados no arquivo temporário
            if (buffer.size() >= TAM_MEMORIA) {
                Collections.sort(buffer, Comparator.comparing(Pokemon::getName)); // Ordena os Pokemons no buffer por ID
                escreverBuffer(buffer, alternador ? temp1 : temp2); // Escreve os Pokemons no arquivo temporário correspondente
                buffer.clear(); // Limpa o buffer
                alternador = !alternador; // Alterna entre temp1 e temp2
            }
            
            // Lê os dados do arquivo original (pokemon)
            long pos = arquivo.getFilePointer();
            byte lapide = arquivo.readByte(); // Lê o marcador de lapide (indica se o registro está apagado)
            short tam = arquivo.readShort(); // Lê o tamanho do registro
            byte[] dados = new byte[tam]; // Cria um array de bytes para armazenar os dados
            arquivo.read(dados); // Lê os dados do Pokemon

            if (lapide != '*') { // Verifica se o registro não está apagado
                Pokemon p = new Pokemon(); // Cria um objeto Pokemon
                p.fromByteArray(dados); // Converte os dados para um objeto Pokemon
                buffer.add(p); // Adiciona o Pokemon ao buffer
            }
        }
        
        // Após o loop, verifica se ainda há Pokemons no buffer e os escreve no arquivo temporário
        if (!buffer.isEmpty()) {
            Collections.sort(buffer, Comparator.comparing(Pokemon::getName)); // Ordena o buffer
            escreverBuffer(buffer, alternador ? temp1 : temp2); // Escreve no arquivo temporário
        }

        arquivo.close(); // Fecha o arquivo original
        temp1.close(); // Fecha o arquivo temporário 1
        temp2.close(); // Fecha o arquivo temporário 2
    }

    // Método para escrever os Pokemons do buffer no arquivo
    private static void escreverBuffer(List<Pokemon> buffer, RandomAccessFile arquivo) throws IOException {
        for (Pokemon p : buffer) {
            byte[] dados = p.toByteArray(); // Converte o Pokemon para um array de bytes
            arquivo.writeByte(' '); // Escreve um marcador de lapide (não apagado)
            arquivo.writeShort(dados.length); // Escreve o tamanho dos dados
            arquivo.write(dados); // Escreve os dados do Pokemon
        }
    }

    // Método para intercalar os arquivos temporários e gravar no arquivo final
    private static void intercalarArquivos() throws Exception {
        RandomAccessFile temp1 = new RandomAccessFile(TEMP1, "r"); // Abre o arquivo temporário 1 para leitura
        RandomAccessFile temp2 = new RandomAccessFile(TEMP2, "r"); // Abre o arquivo temporário 2 para leitura
        RandomAccessFile arquivoFinal = new RandomAccessFile(ARQUIVO_ORIGINAL, "rw"); // Abre o arquivo original para escrita final

        arquivoFinal.setLength(0); // Limpa o conteúdo do arquivo original
        arquivoFinal.writeInt(0); // Escreve o cabeçalho com um valor inicial (último ID)

        // Lê o próximo Pokemon de cada arquivo temporário
        Pokemon p1 = lerProximo(temp1);
        Pokemon p2 = lerProximo(temp2);
        
        // Loop para intercalar os registros de ambos os arquivos temporários
        while (p1 != null || p2 != null) {
            // Compara os Pokemons e escreve o menor (por ID) no arquivo final
            if (p1 != null && (p2 == null || p1.getName().compareTo(p2.getName()) < 0)) {
                escreverRegistro(arquivoFinal, p1); // Escreve o Pokemon de temp1
                p1 = lerProximo(temp1); // Lê o próximo Pokemon de temp1
            } else {
                escreverRegistro(arquivoFinal, p2); // Escreve o Pokemon de temp2
                p2 = lerProximo(temp2); // Lê o próximo Pokemon de temp2
            }
        }

        temp1.close(); // Fecha o arquivo temporário 1
        temp2.close(); // Fecha o arquivo temporário 2
        arquivoFinal.close(); // Fecha o arquivo final
    }

    // Método para ler o próximo Pokemon de um arquivo
    private static Pokemon lerProximo(RandomAccessFile arquivo) throws IOException {
        if (arquivo.getFilePointer() >= arquivo.length()) {
            return null; // Se chegou ao final do arquivo, retorna null
        }

        byte lapide = arquivo.readByte(); // Lê o marcador de lapide
        short tam = arquivo.readShort(); // Lê o tamanho dos dados
        byte[] dados = new byte[tam]; // Cria um array para armazenar os dados
        arquivo.read(dados); // Lê os dados

        if (lapide == '*') { // Se o registro estiver apagado, lê o próximo
            return lerProximo(arquivo);
        }

        Pokemon p = new Pokemon(); // Cria um novo Pokemon
        p.fromByteArray(dados); // Converte os dados para um objeto Pokemon
        return p; // Retorna o Pokemon
    }

    // Método para escrever um Pokemon no arquivo final
    private static void escreverRegistro(RandomAccessFile arquivo, Pokemon p) throws IOException {
        byte[] dados = p.toByteArray(); // Converte o Pokemon para um array de bytes
        arquivo.writeByte(' '); // Escreve um marcador de lapide (não apagado)
        arquivo.writeShort(dados.length); // Escreve o tamanho dos dados
        arquivo.write(dados); // Escreve os dados do Pokemon
    }
}

         // CRUD

         public static int create(Pokemon p)throws Exception {
            RandomAccessFile arquivo = new RandomAccessFile("pokemons.db", "rw");
            tp2hash sistema = new tp2hash();
            // Verifica se o arquivo está vazio (primeira execução)
            if (arquivo.length() == 0) {
                arquivo.writeInt(0); // Escreve o último ID como 0
            }
    
            // Vai para a posição onde o último ID está gravado e lê
            arquivo.seek(0);
            int ultimoId = arquivo.readInt();
    
            // Seta o ID do novo Pokémon
            p.setId(ultimoId + 1);
            int id = ultimoId+1;
    
            // Volta para o início para atualizar o último ID
            arquivo.seek(0);
            arquivo.writeInt(p.getId());
    
            // Cria o vetor de bytes com os dados do Pokémon
            byte[] dados = p.toByteArray();

    
            // Vai para o fim do arquivo para escrever o novo Pokémon
            arquivo.seek(arquivo.length());
            long endereco = arquivo.getFilePointer();
            arquivo.write(' '); // Escreve a "lapide"
            arquivo.writeShort(dados.length);     // Escreve o tamanho do registro
            arquivo.write(dados);      // Escreve os dados do Pokémon
            
            sistema.indiceDireto.create(new ParIDEndereco(id, endereco));

            

            arquivo.close();
           return id;
     
        
    }

    public Pokemon read(int id)throws Exception{
            RandomAccessFile arquivo = new RandomAccessFile("pokemons.db", "r");

            arquivo.seek(TAM_CABECALHO);//vou para posiçao apos o cabeçalho.

            while(arquivo.getFilePointer()<arquivo.length()){
                byte lapide = arquivo.readByte();  //leio a lapide.
                short tam = arquivo.readShort();  //leio o tamanho do registro.
                byte[] dados = new byte[tam];  //crio um vetor de bytes.
                
                arquivo.read(dados);// leio os dados do registro no vetor.
                if(lapide!='*'){ //  verifico se esta excluido.
                    Pokemon p = new Pokemon();
                    p.fromByteArray(dados);  //extraio os dados em um objeto.
                    if(p.getId()==id){  //verifico se o id é igual ao do pokemon buscado.
                        return p;
                    }
                }
                
            }
            arquivo.close();
            return null;
 
    }

    public boolean update(Pokemon novoP)throws Exception{
        tp1 sistema = new tp1();
        RandomAccessFile arquivo = new RandomAccessFile("pokemons.db", "rw");

        arquivo.seek(TAM_CABECALHO);//vou para posiçao apos o cabeçalho.

        while(arquivo.getFilePointer()<arquivo.length()){  //enquanto nao atingir o fim do arquivo.
           long pos = arquivo.getFilePointer();  // Salva a posição atual do registro
           byte lapide = arquivo.readByte();  //le a lapide.
           short tam = arquivo.readShort();  //le o tamanho do registro.
           byte[] dados = new byte[tam];  //crio um vetor de bytes.
           arquivo.read(dados);  //leio o registro e gravo no vetor.

           if(lapide != '*'){   //verifico se esta excluido.
               Pokemon p = new Pokemon();
               p.fromByteArray(dados);   //extraio os dados em um objeto.

               if(p.getId()==novoP.getId()){
                   byte[] novosDados = novoP.toByteArray();

                   if(novosDados.length <= tam){  // verifico se o novo registro tiver um tamanho menor ou igual ao do que esta sendo atualizado.
                     arquivo.seek(pos + 3);  //vou para a posiçao do registro e pulo a lapide e o tamanho 
                     arquivo.write(novosDados);  //escreve o novo registro.

                   }else{
                     arquivo.seek(pos);     //vou para a posição.
                     arquivo.write('*');  //marco como excluido.
                     
                     //escreve o registro no fim do arquivo.
                     arquivo.seek(arquivo.length());  //vou para o fim do arquivo.
                     arquivo.write(' ');  //escreve a lapide.
                     arquivo.writeShort(novosDados.length);  //escreve o tamanho do registro.
                     arquivo.write(novosDados);  //escreve os dados.
                   }
                   return true;  // Retorna true se o registro foi atualizado
               }
           }
        } 
        return false;   // Retorna false se o ID não for encontrado
    }

    public boolean delete(int id)throws Exception{
        RandomAccessFile arquivo = new RandomAccessFile("pokemons.db", "rw");
        arquivo.seek(TAM_CABECALHO);

        while(arquivo.getFilePointer()<arquivo.length()){
            long pos = arquivo.getFilePointer();  // Salva a posição atual do registro
            byte lapide = arquivo.readByte();  //leio a lapide.
            short tam = arquivo.readShort();  // leio o tamanho do registro.
            byte[] dados = new byte[tam];  //crio um vetor de bytes.

            arquivo.read(dados);  // leio os dados do registro no vetor.
            if(lapide!='*'){  // verifico se esta excluido.
               Pokemon p = new Pokemon();
               p.fromByteArray(dados);  //extraio os dados em um objeto.
               if(p.getId()==id){  //verifico se o id é igual ao do pokemon buscado.
                  arquivo.seek(pos);
                  arquivo.write('*'); //marco a lapide como excluido.
                  return true;  //retorna true se for excluido.
               }
            }

        }
        return false;
    }

    public static void main(String []args){
        
          // Criar uma instância de tp1
    tp2hash sistema = new tp2hash(); 
    String nomeArquivo = "pokemons";

    try{
      if(Carregado()==false){
         
        indiceDireto = new HashExtensivel<>(
          ParIDEndereco.class.getConstructor(), 
          4, 
          ".\\dados\\"+na+"\\"+na+".d.db", // diretório 
          ".\\dados\\"+na+"\\"+na+".c.db"  // cestos
      );

          System.out.println("Importando CSV...");
          importarCSVParaBD();// Isso vai importar o CSV e salvar no arquivo/banco
          System.out.println("CSV importado com sucesso!");
      }
      
  }catch(Exception e){
      System.out.println("Erro do sistema.");
          e.printStackTrace();
  }

        int opcao; 
       do{

       
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\nAEDsIII");
        System.out.println("Menu de Pokemons");
        System.out.println("----------------");
        System.out.println("Escolha uma opção");
        System.out.println("----------------");
        System.out.println("\n1-Buscar.");
        System.out.println("2-Inserir.");
        System.out.println("3-Atualizar.");
        System.out.println("4-Excluir.");
        System.out.println("5-Ordenar.");
        System.out.println("0-Sair.");

        System.out.println("\nOpção:");

         opcao = sc.nextInt();
        sc.nextLine();
        
            switch (opcao) {
                    case 1:
                     buscarPokemon();
                    break;
                    case 2:
                     inserirPokemon();
                    break;
                    case 3:
                     atualizarPokemon();
                    break;
                    case 4:
                     excluirPokemon();
                    break;
                    case 5:
                    try {
                    
                        System.out.println("Ordenando o arquivo...");
                        OrdenacaoExterna.ordenarArquivo();
                        System.out.println("Arquivo ordenado com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro ao ordenar o arquivo: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;
            
                default:
                    break;
            }
        }while(opcao!=0);
    
       
        
    }

  
    public static void buscarPokemon(){
        tp2hash sistema = new tp2hash();
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o Id do Pokemon:");
        int id;
        id = sc.nextInt();
        sc.nextLine(); // Consumir quebra de linha

        if (id>0){
            try{
                Pokemon pokemon = new Pokemon();
                pokemon = sistema.read(id);
                if(pokemon != null){
                    System.out.println(pokemon);
                }
                else{
                    System.out.println("O Pokemon não foi encontrado");
                }
            }catch(Exception e){
                System.out.println("Erro do sistema. Não foi possível buscar o Pokemon!");
                e.printStackTrace();
            }
        }else{
            System.out.println("Id Invalido");
        }
    }
    public static void inserirPokemon(){
        tp2hash sistema = new tp2hash();
        Scanner sc = new Scanner(System.in);
        Pokemon novoP = new Pokemon();
        String nome = "";
        String descricao = "";
        String tipo1 = "";
        String tipo2 = "";
        String habilidades = "";
        float peso = 0;
        float altura = 0;
        int taxaCaptura = 0;
        boolean lendario = false;
        LocalDate dataCaptura = null;
        boolean dadosCorretos = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    
        try {
    
            System.out.println("\n--- Inclusão de Pokémon ---");
    
        // Nome
    // Lendo os atributos do pokemon
    System.out.print("Nome: ");
    nome = sc.nextLine();
    novoP.setName(nome);
    
    System.out.print("Geração: ");
    int geracao = Integer.parseInt(sc.nextLine());
    novoP.setGeneration(geracao);
    
    System.out.print("Descrição: ");
    descricao = sc.nextLine();
    novoP.setDescription(descricao);
    
    // Captura dos tipos (permitindo até dois)
    
    System.out.print("Tipo 1: ");
    tipo1 = sc.nextLine();
    
    if (!tipo1.isEmpty()) {
    novoP.addType(tipo1);;
    }
    
    System.out.print("Tipo 2 (deixe em branco se não houver): ");
    tipo2 = sc.nextLine();
    if (!tipo2.isEmpty()) {
    novoP.addType(tipo2);;
    }
    
    // Captura das habilidades (permitindo até duas)
    
    System.out.print("Habilidade 1: ");
    String habilidade1 = sc.nextLine();
    if (!habilidade1.isEmpty()) {
    novoP.addAbility(habilidade1);;
    }
    
    System.out.print("Habilidade 2 (deixe em branco se não houver): ");
    String habilidade2 = sc.nextLine();
    if (!habilidade2.isEmpty()) {
    novoP.addAbility(habilidade2);;
    }
    
    // Outros atributos
    System.out.print("Peso (kg): ");
    peso = Integer.parseInt(sc.nextLine());
    novoP.setWeight(peso);
    
    System.out.print("Altura (cm): ");
    altura = Integer.parseInt(sc.nextLine());
    novoP.setHeight(altura);
    
    System.out.print("Taxa de captura: ");
    taxaCaptura = Integer.parseInt(sc.nextLine());
    novoP.setCaptureRate(taxaCaptura);
    
    // Lendário ou não
    System.out.print("É lendário? (S/N): ");
    lendario = sc.nextLine().equalsIgnoreCase("S");
    novoP.setIsLegendary(lendario);
    
    // Data de captura
    System.out.print("Data de captura (dd/MM/yyyy): ");
    String dataCapturaStr = sc.nextLine();
    dataCaptura = novoP.parseDate(dataCapturaStr);
    novoP.setCaptureDate(dataCaptura);
    
    
    // Adicionando ao arquivo
    int idGerado = sistema.create(novoP);
    System.out.println("Pokémon cadastrado com sucesso! ID: " + idGerado);
    System.out.println(novoP);
    
    } catch (Exception e) {
    System.out.println("Erro ao incluir Pokémon!");
    e.printStackTrace();
    }
    }

    public static void atualizarPokemon() {
        tp2hash sistema = new tp2hash();
        Scanner sc = new Scanner(System.in);
        System.out.print("\nDigite o ID do Pokemon a ser alterado: ");
        int id = sc.nextInt();  // Lê o ID do pokemon
        sc.nextLine();  // Limpar o buffer após o nextInt()
    
        if(id > 0) {
            try {
                // Lê o pokemon e carrega ele para um novo pokemon
                Pokemon p = sistema.read(id);
    
                if(p != null) {
                    System.out.println("Pokemon encontrado");
                    System.out.println(p);
    
                    // Alteração do Nome
                    System.out.print("\nNovo nome (deixe em branco para manter o anterior): ");
                    String novoNome = sc.nextLine();
                    if (!novoNome.isEmpty()) {
                        p.setName(novoNome);
                    }
    
                    // Alteração da Geração
                    System.out.print("\nNova geração (deixe em branco para manter a anterior): ");
                    String novaGeracao = sc.nextLine();
                    if (!novaGeracao.isEmpty()) {
                        p.setGeneration(Integer.parseInt(novaGeracao));
                    }
    
                    // Alteração da Descrição
                    System.out.print("\nNova descrição (deixe em branco para manter a anterior): ");
                    String novaDescricao = sc.nextLine();
                    if (!novaDescricao.isEmpty()) {
                        p.setDescription(novaDescricao);
                    }
    
                    // Alteração dos Tipos
                    List<String> novosTipos = new ArrayList<>(p.getTypes());
                    System.out.print("\nNovo tipo 1 (deixe em branco para manter o anterior): ");
                    String novoTipo1 = sc.nextLine();
                    if (!novoTipo1.isEmpty()) {
                        // Modifica o tipo 1
                        if (novosTipos.isEmpty()) novosTipos.add(novoTipo1);
                        else novosTipos.set(0, novoTipo1);
                    }
                    System.out.print("\nNovo tipo 2 (deixe em branco para manter o anterior): ");
                    String novoTipo2 = sc.nextLine();
                    if (!novoTipo2.isEmpty()) {
                        // Modifica ou adiciona o tipo 2
                        if (novosTipos.size() < 2) novosTipos.add(novoTipo2);
                        else novosTipos.set(1, novoTipo2);
                    }
                    p.setTypes(novosTipos);
    
                    // Alteração das Habilidades
                    List<String> novasHabilidades = new ArrayList<>(p.getAbilities());
                    System.out.print("\nNova habilidade 1 (deixe em branco para manter a anterior): ");
                    String novaHabilidade1 = sc.nextLine();
                    if (!novaHabilidade1.isEmpty()) {
                        // Modifica a habilidade 1
                        if (novasHabilidades.isEmpty()) novasHabilidades.add(novaHabilidade1);
                        else novasHabilidades.set(0, novaHabilidade1);
                    }
                    System.out.print("\nNova habilidade 2 (deixe em branco para manter a anterior): ");
                    String novaHabilidade2 = sc.nextLine();
                    if (!novaHabilidade2.isEmpty()) {
                        // Modifica ou adiciona a habilidade 2
                        if (novasHabilidades.size() < 2) novasHabilidades.add(novaHabilidade2);
                        else novasHabilidades.set(1, novaHabilidade2);
                    }
                    p.setAbilities(novasHabilidades);
    
                    // Alteração de peso
                    System.out.print("\nNovo peso (deixe em branco para manter o anterior): ");
                    String novoPeso = sc.nextLine();
                    if(!novoPeso.isEmpty()){
                        p.setWeight(Float.parseFloat(novoPeso)); // Conversão correta para float
                    }
    
                    // Alteração de altura
                    System.out.print("\nNova altura (deixe em branco para manter o anterior): ");
                    String novoAltura = sc.nextLine();
                    if(!novoAltura.isEmpty()){
                        p.setHeight(Float.parseFloat(novoAltura)); // Conversão correta para float
                    }
    
                    // Alteração de taxa de captura
                    System.out.print("\nNova taxa de captura (deixe em branco para manter o anterior): ");
                    String novoCaptureRate = sc.nextLine();
                    if(!novoCaptureRate.isEmpty()){
                        p.setCaptureRate(Integer.parseInt(novoCaptureRate));
                    }
    
                    // Alteração de Lendário
                    System.out.print("\nNovo Lendário (deixe em branco para manter o anterior): ");
                    String novoLegendary = sc.nextLine();
                    if(!novoLegendary.isEmpty()){
                        if(novoLegendary.equals("S") || novoLegendary.equals("s")) {
                            p.setIsLegendary(true);
                        } else if(novoLegendary.equals("N") || novoLegendary.equals("n")) {
                            p.setIsLegendary(false);
                        }
                    }
    
                    // Alteração de Data de Captura
                    System.out.print("\nNova data de captura (deixe em branco para manter o anterior): ");
                    String novoCaptureDate = sc.nextLine();
                    if(!novoCaptureDate.isEmpty()){
                        p.setCaptureDate(p.parseDate(novoCaptureDate)); // Usando o método parseDate
                    }
    
                    // Confirmação da alteração
                    System.out.print("\nConfirma as alterações? (S/N) ");
                    char resp = sc.next().charAt(0);
                    sc.nextLine(); // Limpa o buffer para não afetar o próximo input
                    
                    if (resp == 'S' || resp == 's') {
                        // Salva as alterações no arquivo
                        boolean alterado = sistema.update(p);
                        if (alterado) {
                            System.out.println("Pokemon alterado com sucesso.");
                        } else {
                            System.out.println("Erro ao alterar o Pokemon.");
                        }
                    } else {
                        System.out.println("Alterações canceladas.");
                    }
                } else {
                    System.out.println("Pokemon não encontrado.");
                }
            } catch (Exception e) {
                System.out.println("Erro do sistema. Não foi possível alterar o Pokemon!");
                e.printStackTrace();
            }
        } else {
            System.out.println("ID inválido.");
        }
    }
    

    public static void excluirPokemon() {
        tp2hash sistema = new tp2hash();
        Scanner sc = new Scanner(System.in);
        
        System.out.println("\nDigite o Id do Pokemon para excluir:");
        int id = sc.nextInt();
        sc.nextLine();

        if(id>0){
            try{
                Pokemon p = sistema.read(id);
                if(p!=null){
                    System.out.println("Pokemon encontrado.");
                    System.out.println(p);

                    System.out.println("Deseja excluir este Pokemon ? (S/N)");
                    char resp = sc.next().charAt(0);
                    
                    if(resp == 'S' || resp == 's'){
                        boolean excluido = sistema.delete(id);
                        sc.nextLine(); // Consumir quebra de linha
                        if(excluido){
                            System.out.println("Pokemon excluído com sucesso.");
                        }
                        else{
                            System.out.println("Erro ao excluir Pokemon.");
                        }
                    }else{
                        System.out.println("Exclusão cancelada.");
                    }
                }else{
                 System.out.println("Pokemon não encontrado.");
                    }
                
            }catch(Exception e){
                System.out.println("Erro do sistema. Não foi possível excluir o cliente!");
                e.printStackTrace();
            }
        }else{
            System.out.println("Id inválido.");
        }
    }


    public class HashExtensivel<T extends RegistroHashExtensivel<T>> {

      String nomeArquivoDiretorio;
      String nomeArquivoCestos;
      RandomAccessFile arqDiretorio;
      RandomAccessFile arqCestos;
      int quantidadeDadosPorCesto;
      Diretorio diretorio;
      Constructor<T> construtor;
    
      public class Cesto {
    
        Constructor<T> construtor;
        short quantidadeMaxima; // quantidade máxima de elementos que o cesto pode conter
        short bytesPorElemento; // tamanho fixo de cada elemento em bytes
        short bytesPorCesto; // tamanho fixo do cesto em bytes
    
        byte profundidadeLocal; // profundidade local do cesto
        short quantidade; // quantidade de elementos presentes no cesto
        ArrayList<T> elementos; // sequência de elementos armazenados
    
        public Cesto(Constructor<T> ct, int qtdmax) throws Exception {
          this(ct, qtdmax, 0);
        }
    
        public Cesto(Constructor<T> ct, int qtdmax, int pl) throws Exception {
          construtor = ct;
          if (qtdmax > 32767)
            throw new Exception("Quantidade máxima de 32.767 elementos");
          if (pl > 127)
            throw new Exception("Profundidade local máxima de 127 bits");
          profundidadeLocal = (byte) pl;
          quantidade = 0;
          quantidadeMaxima = (short) qtdmax;
          elementos = new ArrayList<>(quantidadeMaxima);
          bytesPorElemento = ct.newInstance().size();
          bytesPorCesto = (short) (bytesPorElemento * quantidadeMaxima + 3);
        }
    
        public byte[] toByteArray() throws Exception {
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          DataOutputStream dos = new DataOutputStream(baos);
          dos.writeByte(profundidadeLocal);
          dos.writeShort(quantidade);
          int i = 0;
          while (i < quantidade) {
            dos.write(elementos.get(i).toByteArray());
            i++;
          }
          byte[] vazio = new byte[bytesPorElemento];
          while (i < quantidadeMaxima) {
            dos.write(vazio);
            i++;
          }
          return baos.toByteArray();
        }
    
        public void fromByteArray(byte[] ba) throws Exception {
          ByteArrayInputStream bais = new ByteArrayInputStream(ba);
          DataInputStream dis = new DataInputStream(bais);
          profundidadeLocal = dis.readByte();
          quantidade = dis.readShort();
          int i = 0;
          elementos = new ArrayList<>(quantidadeMaxima);
          byte[] dados = new byte[bytesPorElemento];
          T elem;
          while (i < quantidadeMaxima) {
            dis.read(dados);
            elem = construtor.newInstance();
            elem.fromByteArray(dados);
            elementos.add(elem);
            i++;
          }
        }
    
        // Inserir elementos no cesto
        public boolean create(T elem) {
          if (full())
            return false;
          int i = quantidade - 1; // posição do último elemento no cesto
          while (i >= 0 && elem.hashCode() < elementos.get(i).hashCode())
            i--;
          elementos.add(i + 1, elem);
          quantidade++;
          return true;
        }
    
        // Buscar um elemento no cesto
        public T read(int chave) {
          if (empty())
            return null;
          int i = 0;
          while (i < quantidade && chave > elementos.get(i).hashCode())
            i++;
          if (i < quantidade && chave == elementos.get(i).hashCode())
            return elementos.get(i);
          else
            return null;
        }
    
        // atualizar um elemento do cesto
        public boolean update(T elem) {
          if (empty())
            return false;
          int i = 0;
          while (i < quantidade && elem.hashCode() > elementos.get(i).hashCode())
            i++;
          if (i < quantidade && elem.hashCode() == elementos.get(i).hashCode()) {
            elementos.set(i, elem);
            return true;
          } else
            return false;
        }
    
        // pagar um elemento do cesto
        public boolean delete(int chave) {
          if (empty())
            return false;
          int i = 0;
          while (i < quantidade && chave > elementos.get(i).hashCode())
            i++;
          if (chave == elementos.get(i).hashCode()) {
            elementos.remove(i);
            quantidade--;
            return true;
          } else
            return false;
        }
    
        public boolean empty() {
          return quantidade == 0;
        }
    
        public boolean full() {
          return quantidade == quantidadeMaxima;
        }
    
        public String toString() {
          String s = "Profundidade Local: " + profundidadeLocal + "\nQuantidade: " + quantidade + "\n| ";
          int i = 0;
          while (i < quantidade) {
            s += elementos.get(i).toString() + " | ";
            i++;
          }
          while (i < quantidadeMaxima) {
            s += "- | ";
            i++;
          }
          return s;
        }
    
        public int size() {
          return bytesPorCesto;
        }
    
      }
    
      protected class Diretorio {
    
        byte profundidadeGlobal;
        long[] enderecos;
    
        public Diretorio() {
          profundidadeGlobal = 0;
          enderecos = new long[1];
          enderecos[0] = 0;
        }
    
        public boolean atualizaEndereco(int p, long e) {
          if (p > Math.pow(2, profundidadeGlobal))
            return false;
          enderecos[p] = e;
          return true;
        }
    
        public byte[] toByteArray() throws IOException {
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          DataOutputStream dos = new DataOutputStream(baos);
          dos.writeByte(profundidadeGlobal);
          int quantidade = (int) Math.pow(2, profundidadeGlobal);
          int i = 0;
          while (i < quantidade) {
            dos.writeLong(enderecos[i]);
            i++;
          }
          return baos.toByteArray();
        }
    
        public void fromByteArray(byte[] ba) throws IOException {
          ByteArrayInputStream bais = new ByteArrayInputStream(ba);
          DataInputStream dis = new DataInputStream(bais);
          profundidadeGlobal = dis.readByte();
          int quantidade = (int) Math.pow(2, profundidadeGlobal);
          enderecos = new long[quantidade];
          int i = 0;
          while (i < quantidade) {
            enderecos[i] = dis.readLong();
            i++;
          }
        }
    
        public String toString() {
          String s = "\nProfundidade global: " + profundidadeGlobal;
          int i = 0;
          int quantidade = (int) Math.pow(2, profundidadeGlobal);
          while (i < quantidade) {
            s += "\n" + i + ": " + enderecos[i];
            i++;
          }
          return s;
        }
    
        protected long endereço(int p) {
          if (p > Math.pow(2, profundidadeGlobal))
            return -1;
          return enderecos[p];
        }
    
        protected boolean duplica() {
          if (profundidadeGlobal == 127)
            return false;
          profundidadeGlobal++;
          int q1 = (int) Math.pow(2, profundidadeGlobal - 1);
          int q2 = (int) Math.pow(2, profundidadeGlobal);
          long[] novosEnderecos = new long[q2];
          int i = 0;
          while (i < q1) { // copia o vetor anterior para a primeiro metade do novo vetor
            novosEnderecos[i] = enderecos[i];
            i++;
          }
          while (i < q2) { // copia o vetor anterior para a segunda metade do novo vetor
            novosEnderecos[i] = enderecos[i - q1];
            i++;
          }
          enderecos = novosEnderecos;
          return true;
        }
    
        // Para efeito de determinar o cesto em que o elemento deve ser inserido,
        // só serão considerados valores absolutos da chave.
        protected int hash(int chave) {
          return Math.abs(chave) % (int) Math.pow(2, profundidadeGlobal);
        }
    
        // Método auxiliar para atualizar endereço ao duplicar o diretório
        protected int hash2(int chave, int pl) { // cálculo do hash para uma dada profundidade local
          return Math.abs(chave) % (int) Math.pow(2, pl);
        }
    
      }
    
      public HashExtensivel(Constructor<T> ct, int n, String nd, String nc) throws Exception {
        construtor = ct;
        quantidadeDadosPorCesto = n;
        nomeArquivoDiretorio = nd;
        nomeArquivoCestos = nc;
    
        arqDiretorio = new RandomAccessFile(nomeArquivoDiretorio, "rw");
        arqCestos = new RandomAccessFile(nomeArquivoCestos, "rw");
    
        // Se o diretório ou os cestos estiverem vazios, cria um novo diretório e lista
        // de cestos
        if (arqDiretorio.length() == 0 || arqCestos.length() == 0) {
    
          // Cria um novo diretório, com profundidade de 0 bits (1 único elemento)
          diretorio = new Diretorio();
          byte[] bd = diretorio.toByteArray();
          arqDiretorio.write(bd);
    
          // Cria um cesto vazio, já apontado pelo único elemento do diretório
          Cesto c = new Cesto(construtor, quantidadeDadosPorCesto);
          bd = c.toByteArray();
          arqCestos.seek(0);
          arqCestos.write(bd);
        }
      }
    
      public boolean create(T elem) throws Exception {
    
        // Carrega TODO o diretório para a memória
        byte[] bd = new byte[(int) arqDiretorio.length()];
        arqDiretorio.seek(0);
        arqDiretorio.read(bd);
        diretorio = new Diretorio();
        diretorio.fromByteArray(bd);
    
        // Identifica a hash do diretório,
        int i = diretorio.hash(elem.hashCode());
    
        // Recupera o cesto
        long enderecoCesto = diretorio.endereço(i);
        Cesto c = new Cesto(construtor, quantidadeDadosPorCesto);
        byte[] ba = new byte[c.size()];
        arqCestos.seek(enderecoCesto);
        arqCestos.read(ba);
        c.fromByteArray(ba);
    
        // Testa se a chave já não existe no cesto
        if (c.read(elem.hashCode()) != null)
          throw new Exception("Elemento já existe");
    
        // Testa se o cesto já não está cheio
        // Se não estiver, create o par de chave e dado
        if (!c.full()) {
          // Insere a chave no cesto e o atualiza
          c.create(elem);
          arqCestos.seek(enderecoCesto);
          arqCestos.write(c.toByteArray());
          return true;
        }
    
        // Duplica o diretório
        byte pl = c.profundidadeLocal;
        if (pl >= diretorio.profundidadeGlobal)
          diretorio.duplica();
        byte pg = diretorio.profundidadeGlobal;
    
        // Cria os novos cestos, com os seus dados no arquivo de cestos
        Cesto c1 = new Cesto(construtor, quantidadeDadosPorCesto, pl + 1);
        arqCestos.seek(enderecoCesto);
        arqCestos.write(c1.toByteArray());
    
        Cesto c2 = new Cesto(construtor, quantidadeDadosPorCesto, pl + 1);
        long novoEndereco = arqCestos.length();
        arqCestos.seek(novoEndereco);
        arqCestos.write(c2.toByteArray());
    
        // Atualiza os dados no diretório
        int inicio = diretorio.hash2(elem.hashCode(), c.profundidadeLocal);
        int deslocamento = (int) Math.pow(2, pl);
        int max = (int) Math.pow(2, pg);
        boolean troca = false;
        for (int j = inicio; j < max; j += deslocamento) {
          if (troca)
            diretorio.atualizaEndereco(j, novoEndereco);
          troca = !troca;
        }
    
        // Atualiza o arquivo do diretório
        bd = diretorio.toByteArray();
        arqDiretorio.seek(0);
        arqDiretorio.write(bd);
    
        // Reinsere as chaves do cesto antigo
        for (int j = 0; j < c.quantidade; j++) {
          create(c.elementos.get(j));
        }
        create(elem); // insere o nome elemento
        return true;
    
      }
    
      public T read(int chave) throws Exception {
    
        // Carrega o diretório
        byte[] bd = new byte[(int) arqDiretorio.length()];
        arqDiretorio.seek(0);
        arqDiretorio.read(bd);
        diretorio = new Diretorio();
        diretorio.fromByteArray(bd);
    
        // Identifica a hash do diretório,
        int i = diretorio.hash(chave);
    
        // Recupera o cesto
        long enderecoCesto = diretorio.endereço(i);
        Cesto c = new Cesto(construtor, quantidadeDadosPorCesto);
        byte[] ba = new byte[c.size()];
        arqCestos.seek(enderecoCesto);
        arqCestos.read(ba);
        c.fromByteArray(ba);
    
        return c.read(chave);
      }
    
      public boolean update(T elem) throws Exception {
    
        // Carrega o diretório
        byte[] bd = new byte[(int) arqDiretorio.length()];
        arqDiretorio.seek(0);
        arqDiretorio.read(bd);
        diretorio = new Diretorio();
        diretorio.fromByteArray(bd);
    
        // Identifica a hash do diretório,
        int i = diretorio.hash(elem.hashCode());
    
        // Recupera o cesto
        long enderecoCesto = diretorio.endereço(i);
        Cesto c = new Cesto(construtor, quantidadeDadosPorCesto);
        byte[] ba = new byte[c.size()];
        arqCestos.seek(enderecoCesto);
        arqCestos.read(ba);
        c.fromByteArray(ba);
    
        // atualiza o dado
        if (!c.update(elem))
          return false;
    
        // Atualiza o cesto
        arqCestos.seek(enderecoCesto);
        arqCestos.write(c.toByteArray());
        return true;
    
      }
    
      public boolean delete(int chave) throws Exception {
    
        // Carrega o diretório
        byte[] bd = new byte[(int) arqDiretorio.length()];
        arqDiretorio.seek(0);
        arqDiretorio.read(bd);
        diretorio = new Diretorio();
        diretorio.fromByteArray(bd);
    
        // Identifica a hash do diretório,
        int i = diretorio.hash(chave);
    
        // Recupera o cesto
        long enderecoCesto = diretorio.endereço(i);
        Cesto c = new Cesto(construtor, quantidadeDadosPorCesto);
        byte[] ba = new byte[c.size()];
        arqCestos.seek(enderecoCesto);
        arqCestos.read(ba);
        c.fromByteArray(ba);
    
        // delete a chave
        if (!c.delete(chave))
          return false;
    
        // Atualiza o cesto
        arqCestos.seek(enderecoCesto);
        arqCestos.write(c.toByteArray());
        return true;
      }
    
      public void print() {
        try {
          byte[] bd = new byte[(int) arqDiretorio.length()];
          arqDiretorio.seek(0);
          arqDiretorio.read(bd);
          diretorio = new Diretorio();
          diretorio.fromByteArray(bd);
          System.out.println("\nDIRETÓRIO ------------------");
          System.out.println(diretorio);
    
          System.out.println("\nCESTOS ---------------------");
          arqCestos.seek(0);
          while (arqCestos.getFilePointer() != arqCestos.length()) {
            System.out.println("Endereço: " + arqCestos.getFilePointer());
            Cesto c = new Cesto(construtor, quantidadeDadosPorCesto);
            byte[] ba = new byte[c.size()];
            arqCestos.read(ba);
            c.fromByteArray(ba);
            System.out.println(c + "\n");
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    
      public void close() throws Exception {
        arqDiretorio.close();
        arqCestos.close();
      }
    }

      public interface RegistroHashExtensivel<T> {

        public int hashCode(); // chave numérica para ser usada no diretório
      
        public short size(); // tamanho FIXO do registro
      
        public byte[] toByteArray() throws IOException; // representação do elemento em um vetor de bytes
      
        public void fromByteArray(byte[] ba) throws IOException; // vetor de bytes a ser usado na construção do elemento
      
      }

      public class ParIDEndereco implements RegistroHashExtensivel<ParIDEndereco> {
    
        private int id;   // chave
        private long endereco;    // valor
        private final short TAMANHO = 12;  // tamanho em bytes
    
        public ParIDEndereco() {
            this.id = -1;
            this.endereco = -1;
        }
    
        public ParIDEndereco(int id, long end) {
            this.id = id;
            this.endereco = end;
        }
    
        public int getId() {
            return id;
        }
    
        public long getEndereco() {
            return endereco;
        }
    
        @Override
        public int hashCode() {
            return this.id;
        }
    
        public short size() {
            return this.TAMANHO;
        }
    
        public String toString() {
            return "("+this.id + ";" + this.endereco+")";
        }
    
        public byte[] toByteArray() throws IOException {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            dos.writeInt(this.id);
            dos.writeLong(this.endereco);
            return baos.toByteArray();
        }
    
        public void fromByteArray(byte[] ba) throws IOException {
            ByteArrayInputStream bais = new ByteArrayInputStream(ba);
            DataInputStream dis = new DataInputStream(bais);
            this.id = dis.readInt();
            this.endereco = dis.readLong();
        }
    
    }
   
}


