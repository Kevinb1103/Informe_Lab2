import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

         class Voto {
        private int id;
        private int votanteId;
        private int candidatoId;
        private String timeStamp;

 // El constructor de Voto
         public Voto(int id, int votanteId, int candidatoId, String timeStamp) {
             this.id = id;
             this.votanteId = votanteId;
             this.candidatoId = candidatoId;
             this.timeStamp = timeStamp;
         }
    // getters
        public int getId() {return id;}
        public int getVotanteId() {return votanteId;}
        public int getCandidatoId() {
        return candidatoId;
        }
    public String getTimeStamp() {return timeStamp;}
    //setters
    public void setId(int id) {this.id = id;}
    public void setVotanteId(int votanteId) {
        this.votanteId = votanteId;
    }
    public void setCandidatoId(int candidatoId) {
        this.candidatoId = candidatoId;
    }
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}

    class Candidato {
    private int id;
    private String nombre;
    private String partido;
    private Queue<Voto> votosRecibidos;

    //El constructor de Candidato
    public Candidato(int id, String nombre, String partido) {
        this.id = id;
        this.nombre = nombre;
        this.partido = partido;
        this.votosRecibidos = new LinkedList<>();}
    //geters
    public int getId() {return id;}
    public String getNombre() {return nombre;}
    public String getPartido() {return partido;}
    public Queue<Voto> getVotosRecibidos() {return votosRecibidos;}
    //setters
    public void setId(int id) {this.id = id;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setPartido(String partido) {this.partido = partido;}
    public void setVotosRecibidos(Queue<Voto> votosRecibidos) {this.votosRecibidos = votosRecibidos;}
    public void agregarVoto(Voto v) {votosRecibidos.add(v);}
}

    class Votante {
    private int id;
    private String nombre;
    private boolean yaVoto;

    // El contructor de Votante
    public Votante(int id, String nombre, boolean yaVoto) {
        this.id = id;
        this.nombre = nombre;
        this.yaVoto = yaVoto;}
    // Getters
    public int getId() {return id;}
    public String getNombre() {return nombre;}
    public boolean isYaVoto() {return yaVoto;}
    //setters
    public void setId(int id) {this.id = id;}
    public void setNombre(String nombre) {this.nombre = nombre;}
    public void setYaVoto(boolean yaVoto) {this.yaVoto = yaVoto;}
    public void marcarComoVotado() {yaVoto = true;}

}

     class UrnaElectoral{
    private LinkedList<Candidato> listaCandidatos;
    private int idCounter;
    private Queue<Voto> votosReportados;
    private Stack<Voto> historialVotos;

    //Constructor
    public UrnaElectoral() {
        this.listaCandidatos = new LinkedList<>();
        this.votosReportados = new LinkedList<>();
        this.historialVotos = new Stack<>();
        this.idCounter = 0;
    }

    //Metodos

    public boolean verificarVotante(Votante votante){
        return !votante.isYaVoto();
    }

    public boolean registrarVoto(Votante votante, int candidatoId) {
        if (!verificarVotante(votante)) {
            return false;
        }
        Candidato candidatoElegido = null;
        for(Candidato candidato : listaCandidatos) {
            if (candidato.getId() == candidatoId) {
                candidatoElegido = candidato;
                break;
            }
        }
        if (candidatoElegido == null) {
            return false;
        }

        String timeStamp = java.time.LocalDateTime.now().toString();
        Voto nuevoVoto = new Voto(idCounter++, votante.getId(),candidatoId, timeStamp);
        candidatoElegido.agregarVoto(nuevoVoto);
        historialVotos.push(nuevoVoto);
        votante.marcarComoVotado();
        return true;
    }

    public boolean reportarVoto(Candidato candidato,int idVoto){
        Queue<Voto> nuevaCola = new LinkedList<>();
        boolean reportado = false;

        for(Voto voto : candidato.getVotosRecibidos()) {
            if(voto.getId() == idVoto && !reportado) {
                votosReportados.add(voto);
                reportado = true;

            } else {
                nuevaCola.add(voto);
            }
        }

        candidato.setVotosRecibidos(nuevaCola);
        return reportado;
    }

    public String obtenerResultado(){
        StringBuilder resultado = new StringBuilder();

        for(Candidato candidato : listaCandidatos) {
            resultado.append( "Candidato:")
                    .append(candidato.getNombre())
                    .append( " - Votos: ")
                    .append(candidato.getVotosRecibidos().size())
                    .append("\n");
        }
        return resultado.toString();
    }
    public void agregarCandidato(Candidato candidato) {
        listaCandidatos.add(candidato);
    }
}
public class Electo {
    public static void main(String[] args) {
        UrnaElectoral urna = new UrnaElectoral();
        //Creacion de candidatos

        Candidato candidato_1 = new Candidato(1, "Garen", "Demacia");
        Candidato candidato_2 = new Candidato(2, "Darius", "Noxxuz");
        urna.agregarCandidato(candidato_1);
        urna.agregarCandidato(candidato_2);

        //Creando votantes
        Votante votante_1 = new Votante(10, "Lux", false);
        Votante votante_2 = new Votante(11, "Katarina", false);
        Votante votante_3 = new Votante(12, "Galio", false);

        //Votacion
        urna.registrarVoto(votante_1, 1);
        urna.registrarVoto(votante_2, 2);
        urna.registrarVoto(votante_3, 1);

        //Mostrar Votos
        System.out.println(urna.obtenerResultado());

    }
}