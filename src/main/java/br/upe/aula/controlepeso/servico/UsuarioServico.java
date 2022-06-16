package br.upe.aula.controlepeso.servico;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event.ID;

import br.upe.aula.controlepeso.entidade.Usuario;
import br.upe.aula.controlepeso.repositorio.UsuarioRepositorio;

@Service
public class UsuarioServico {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    public Usuario incluir(Usuario usuario) {
        // aplicar regras de negocio

        // 1. Preenchimento de campos obrigatórios
        // 2. Verificar validade email
        // 3. Validar se existe email já cadastrado
        // 4. A Altura deve ser maior do que 100 camada
        // 5. Peso inicial deve ser maior do que 30kg
        // 6. Verificar se a data do peso objetivo deve ser no mínimo de uma semana
        
        usuario.setId(null);
        usuario.setDataInicial(LocalDate.now());
        
        usuario.setPesoAtual(usuario.getPesoInicial());
        atualizarIMC(usuario);
        
       if (usuarioRepositorio.findByEmail(usuario.getEmail()) != null){
        throw new RuntimeException("Já existe um perfil com esta conta");
       }


        if (usuario.getAltura() < 100){
            throw new RuntimeException("A altura mínima é de 100 centímetros");
        }


        if (usuario.getPesoInicial() < 30){
            throw new RuntimeException("O peso mínimo é de 30KG");
        }

        if (usuario.getDataObjetivo().isBefore(usuario.getDataInicial().plus(1, ChronoUnit.WEEKS))){

            throw new RuntimeException("A data de objetivo deve ser maior que uma semana");

        }

        // delegar a camada de dados para salvar no banco de dados
        return usuarioRepositorio.save(usuario);
    }

    public List<Usuario> listar() {
        return usuarioRepositorio.findAll();
    }

    public void excluir(Long id) {

        if (id == null || id == 0l) {
            throw new RuntimeException("Informe um identificador de usuário");
        }

        if (!this.usuarioRepositorio.existsById(id)) {
            throw new RuntimeException("Não existe usuario cadastrado com o identificador:" + id);
        }

        this.usuarioRepositorio.deleteById(id);
    }

    public Usuario alterar(Usuario usuario) {
        // aplicar regras de negocio

        // 0. verificar se existe
        if (usuario == null) {
            throw new RuntimeException("Informe os dados de usuário");
        }

        if (usuario.getId() == null || usuario.getId() == 0l) {
            throw new RuntimeException("Informe um identificador de usuário");
        }

        if (!this.usuarioRepositorio.existsById(usuario.getId())) {
            throw new RuntimeException("Não existe usuario cadastrado com o identificador:" + usuario.getId());
        }

        // 1. Preenchimento de campos obrigatórios
        // 2. Verificar validade email
        // 3. Validar se existe email já cadastrado
        // 4. A Altura deve ser maior do que 100 camada
        // 5. Peso inicial deve ser maior do que 30kg
        // 6. Verificar se a data do peso objetivo deve ser no mínimo de uma semana
        atualizarIMC(usuario);
        usuario.setDataHistorico(LocalDate.now());
        usuario.setPesoComparativo(usuario.getPesoAtual() - usuario.getPesoHistórico());
        return usuarioRepositorio.save(usuario);
    }
    public void atualizarIMC(Usuario usuario){
        Double peso = usuario.getPesoAtual();
        double altura = usuario.getAltura() / 100;
        if (peso / (altura * altura) < 18.5){
            usuario.setPesoClassificação("Magreza");
        }
        if (peso / (altura * altura) > 18.5 && peso / (altura * altura) < 24.9){
            usuario.setPesoClassificação("Normal");
        }
        if (peso / (altura * altura) > 25 && peso / (altura * altura) < 29.9){
            usuario.setPesoClassificação("Sobrepeso");
        }
        if (peso / (altura * altura) > 30 && peso / (altura * altura) < 34.9){
            usuario.setPesoClassificação("Obesidade de primeiro grau");
        }
        if (peso / (altura * altura) > 35 && peso / (altura * altura) < 39.9){
            usuario.setPesoClassificação("Obesidade de segundo grau");
        }
        if (peso / (altura * altura) > 40){
            usuario.setPesoClassificação("Obesidade de terceiro grau");
            
        }
    
    }
    
    public void atualizarProgresso(Usuario usuario){
        usuario.setProgresso(((usuario.getPesoAtual() - usuario.getPesoInicial()) / (usuario.getPesoDesejado() - usuario.getPesoInicial()) * 100));  
    }

    

}
