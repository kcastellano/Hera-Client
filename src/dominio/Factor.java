package dominio;

public class Factor {
	private int idFactor;
    private Double valor;
    private int trimestre;

    public Factor() {
    }

    public Factor(int idFactor, Double valor, int trimestre) {
        this.idFactor = idFactor;
        this.valor = valor;
        this.trimestre = trimestre;
    }

    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
    }

    public int getIdFactor() {
        return idFactor;
    }

    public void setIdFactor(int idFactor) {
        this.idFactor = idFactor;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
