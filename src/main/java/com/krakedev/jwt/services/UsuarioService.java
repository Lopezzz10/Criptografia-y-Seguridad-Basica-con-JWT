package com.krakedev.jwt.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.krakedev.jwt.entidades.Usuario;
import com.krakedev.jwt.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario guardar(Usuario usuario) {
    	String contrasenaEncriptada = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
    	usuario.setPassword(contrasenaEncriptada);
        return usuarioRepository.save(usuario);
    }

    public boolean autenticar(String username, String password) {
        Optional<Usuario> usuarioEncontrado = usuarioRepository.findByUsername(username);
        if (usuarioEncontrado.isPresent()) {
            Usuario usuario = usuarioEncontrado.get();
            if (BCrypt.checkpw(password, usuario.getPassword())) {
                return true;
            }
        }
        return false;
    }
}