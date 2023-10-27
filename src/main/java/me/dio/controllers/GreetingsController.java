package me.dio.controllers;

import me.dio.model.Usuario;
import me.dio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GreetingsController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
    @ResponseStatus (HttpStatus.OK)
    public String retornaOlaMundo (@PathVariable String nome){

        Usuario usuario = new Usuario();
        usuario.setNome(nome);

        usuarioRepository.save(usuario);

        return "Olá Mundo" + nome + "!";
    }

    @GetMapping (value = "listatodos")
    @ResponseBody /*Retorna um json*/
    public ResponseEntity<List<Usuario>> listaUsuario(){

        List<Usuario> usuarios = usuarioRepository.findAll();

        return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    }

    @PostMapping(value = "salvar") /*mapeia a URL*/
    @ResponseBody
    public ResponseEntity<Usuario> salvar (@RequestBody Usuario usuario){

        Usuario user = usuarioRepository.save(usuario);

        return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "delete")
    @ResponseBody
    public ResponseEntity<String> delete(@RequestParam Long iduser){

        usuarioRepository.deleteById(iduser);

        return new ResponseEntity<String>("Usuario deletado com sucesso", HttpStatus.OK);
    }

    @PutMapping(value = "atualizar")
    @ResponseBody
    public ResponseEntity<?> atualizar (@RequestBody Usuario usuario){

        if (usuario.getId() == null) {
            return new ResponseEntity<String>("Id não foi encontrado", HttpStatus.OK);
        }

        Usuario user =  usuarioRepository.saveAndFlush(usuario);

        return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }

    @GetMapping(value = "buscaruserid")
    @ResponseBody
    public ResponseEntity<Usuario> buscaruserid (@RequestParam(name = "iduser") Long iduser){

        Usuario usuario =  usuarioRepository.findById(iduser).get();

        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }

    @GetMapping(value = "buscarPorNome")
    @ResponseBody
    public ResponseEntity<List<Usuario>> buscarPorNome (@RequestParam(name = "name") String name){

        List<Usuario> usuario =  usuarioRepository.buscaPorNome(name.trim().toUpperCase());

        return new ResponseEntity<List<Usuario>> (usuario, HttpStatus.OK);
    }


}
