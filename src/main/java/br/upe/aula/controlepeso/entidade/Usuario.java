package br.upe.aula.controlepeso.entidade;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.ValidationMode;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.type.TrueFalseType;

import antlr.collections.List;
import lombok.Data;

@Entity
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Nos diga o seu nome")
    private String nome;
    @NotBlank(message = "Cadastre um email")
    @Email(message = "Deve ser cadastrado um email correto")
    @Valid
    private String email;
    @NotNull(message = "Altura necessária")
    private Integer altura;

    @NotNull(message = "Informe seu genero")
    @Enumerated(EnumType.STRING)
    private Genero genero;
    @NotNull(message = "Informe o seu peso atual")
    private Double pesoInicial;
    @NotNull(message = ("Insira um peso desejado"))
    private Double pesoDesejado;
    @NotNull(message = "Insira uma data objetivo")
    private LocalDate dataObjetivo;


    private LocalDate dataHistorico;
    private double progresso;
    private double pesoAtual;
    private String pesoClassificação;
    private double pesoComparativo;
    private double pesoHistórico;
    private LocalDate dataInicial;

    
}
