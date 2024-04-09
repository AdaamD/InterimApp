package admd.interim.logic;

public class Offre {
    private int id;
    private String titre;
    private String description;
    private String metier;
    private String lieu;
    private String dateDebut;
    private String dateFin;
    private int idEmployeur;

    // Constructeur
    public Offre(String titre, String description, String metier, String lieu, String dateDebut, String dateFin, int idEmployeur) {
        this.titre = titre;
        this.description = description;
        this.metier = metier;
        this.lieu = lieu;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.idEmployeur = idEmployeur;
    }

    // Getters et setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMetier() {
        return metier;
    }

    public void setMetier(String metier) {
        this.metier = metier;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public int getIdEmployeur() {
        return idEmployeur;
    }

    public void setIdEmployeur(int idEmployeur) {
        this.idEmployeur = idEmployeur;
    }
}
