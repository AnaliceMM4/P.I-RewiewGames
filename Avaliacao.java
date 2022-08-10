/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifsc.slo.tecinfo.pi.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author analice
 */
@Entity
public class Avaliacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int codAvaliacao;
    private String comentario;
    private double nota;
    private int gostei;
    private int naoGostei;
    private String denunciar;
    @ManyToOne
    @JoinColumn(name = "jogo_id")
    private Jogo jogo;

    public Avaliacao() {
    }

    public Avaliacao(int codAvaliacao, String comentario, double nota, int gostei, int naoGostei, String denunciar) {
        this.codAvaliacao = codAvaliacao;
        this.comentario = comentario;
        this.nota = nota;
        this.gostei = gostei;
        this.naoGostei = naoGostei;
        this.denunciar = denunciar;
    }

    public int getCodAvaliacao() {
        return codAvaliacao;
    }

    public void setCodAvaliacao(int codAvaliacao) {
        this.codAvaliacao = codAvaliacao;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public int getGostei() {
        return gostei;
    }

    public void setGostei(int gostei) {
        this.gostei = gostei;
    }

    public int getNaoGostei() {
        return naoGostei;
    }

    public void setNaoGostei(int naoGostei) {
        this.naoGostei = naoGostei;
    }

    public String getDenunciar() {
        return denunciar;
    }

    public void setDenunciar(String denunciar) {
        this.denunciar = denunciar;
    }

    public Jogo getJogo() {
        return jogo;
    }

    public void setJogo(Jogo jogo) {
        this.jogo = jogo;
    }

 
}
