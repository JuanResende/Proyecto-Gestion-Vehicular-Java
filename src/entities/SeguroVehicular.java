package entities;

import java.time.LocalDate;

public class SeguroVehicular {
    private Long id;
    private boolean eliminado;
    private String aseguradora;
    private String nroPoliza;
    private String cobertura; // RC, TERCEROS, TODO_RIESGO
    private LocalDate vencimiento;

    public SeguroVehicular() {}

    public SeguroVehicular(Long id, String aseguradora, String nroPoliza, String cobertura, LocalDate vencimiento) {
        this.id = id;
        this.aseguradora = aseguradora;
        this.nroPoliza = nroPoliza;
        this.cobertura = cobertura;
        this.vencimiento = vencimiento;
        this.eliminado = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public boolean isEliminado() { return eliminado; }
    public void setEliminado(boolean eliminado) { this.eliminado = eliminado; }

    public String getAseguradora() { return aseguradora; }
    public void setAseguradora(String aseguradora) { this.aseguradora = aseguradora; }

    public String getNroPoliza() { return nroPoliza; }
    public void setNroPoliza(String nroPoliza) { this.nroPoliza = nroPoliza; }

    public String getCobertura() { return cobertura; }
    public void setCobertura(String cobertura) { this.cobertura = cobertura; }

    public LocalDate getVencimiento() { return vencimiento; }
    public void setVencimiento(LocalDate vencimiento) { this.vencimiento = vencimiento; }

    @Override
    public String toString() {
        return "SeguroVehicular{" +
                "id=" + id +
                ", aseguradora='" + aseguradora + '\'' +
                ", nroPoliza='" + nroPoliza + '\'' +
                ", cobertura='" + cobertura + '\'' +
                ", vencimiento=" + vencimiento +
                ", eliminado=" + eliminado +
                '}';
    }
}