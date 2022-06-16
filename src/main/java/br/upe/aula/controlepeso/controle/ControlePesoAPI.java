package br.upe.aula.controlepeso.controle;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.upe.aula.controlepeso.entidade.Usuario;

import br.upe.aula.controlepeso.servico.UsuarioServico;

@RestController
@RequestMapping("/api/v1")
public class ControlePesoAPI {

    @Autowired
    private UsuarioServico usuarioServico;




    @GetMapping("/usuarios")
    @ResponseStatus(HttpStatus.OK)
    public List<Usuario> listarUsuarios() {
        return this.usuarioServico.listar();
    }
    
    @DeleteMapping("/usuario/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void apagarUsuarios(@PathVariable Long id) {
        this.usuarioServico.excluir(id);
    }

    @PostMapping("/usuario")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario incluirUsuario(@Valid @RequestBody Usuario usuario) {
        
        return this.usuarioServico.incluir(usuario);
        
    }

    @PutMapping("/usuario/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Usuario alterarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        return this.usuarioServico.alterar(usuario);
    }

}
