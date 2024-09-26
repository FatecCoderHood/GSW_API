package model;

import java.util.UUID;

public class PortalNoticia {

    private String id;
	private String name;
	private String url;
	private String parametrizacao; //json

	public PortalNoticia(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public PortalNoticia( String name) {
		this(UUID.randomUUID().toString(), name);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParametrizacao() {
		return parametrizacao;
	}

	public void setParametrizacao(String parametrizacao) {
		this.parametrizacao = parametrizacao;
	}

}



