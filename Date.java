public class date {
    private int jours;
    private  int  mois;
    private int annee;

    public date(int jours, int mois, int annee) {
        this.jours = jours;
        this.mois = mois;
        this.annee = annee;
    }

    public int getJours() {
        return jours;
    }
    public void setJours(int jours) {
        this.jours = jours;
    }
    public int getMois() {
        return mois;
    }
    public void setMois(int mois) {
        this.mois = mois;
    }
    public int getAnnee() {
        return annee;
    }
    public void setAnnee(int annee) {
        this.annee = annee;
    }

    @Override
    public String toString() {
        return "date{" +
                "jours=" + jours +
                ", mois=" + mois +
                ", annee=" + annee +
                '}';
    }
}