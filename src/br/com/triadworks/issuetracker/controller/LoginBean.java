package br.com.triadworks.issuetracker.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.triadworks.issuetracker.controller.util.FacesUtils;
import br.com.triadworks.issuetracker.dao.UsuarioDao;
import br.com.triadworks.issuetracker.model.Usuario;
import br.com.triadworks.issuetracker.qualifier.UsuarioLogado;
import br.com.triadworks.issuetracker.service.Autenticador;

@Named
@RequestScoped
public class LoginBean {

	private String login;
	private String senha;

	@Inject
	private Autenticador autenticador;
	@Inject 
	private UsuarioWeb usuarioWeb;
	@Inject
	private FacesUtils facesUtils;
	
	@Inject
	private UsuarioDao usuarioDao;

	public String logar() {

		Usuario usuario = autenticador.autentica(login, senha);
		if (usuario != null) {
			usuarioWeb.loga(usuario);
			return "/home?faces-redirect=true"; // outcome
		}

		facesUtils.adicionaMensagemDeErro("Login ou senha invalida.");
		return null;
	}

	@PostConstruct
	public void initUser(){
		List<Usuario> usuarios = usuarioDao.listaTudo();
		if(usuarios != null && !usuarios.isEmpty()){
			Usuario admin = new Usuario();
			admin.setEmail("admin@admin.com");
			admin.setLogin("admin");
			admin.setSenha("admin");
			admin.setNome("Administrator Godlike");
			usuarioDao.salva(admin);
		}
	}
	
	public String sair() {
		usuarioWeb.logout();
		return "login";
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setAutenticador(Autenticador autenticador) {
		this.autenticador = autenticador;
	}

	public void setUsuarioWeb(UsuarioWeb usuarioWeb) {
		this.usuarioWeb = usuarioWeb;
	}

	public void setFacesUtils(FacesUtils facesUtils) {
		this.facesUtils = facesUtils;
	}

}
