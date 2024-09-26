package gsw_api.gsw_api.model;

import java.util.UUID;

public class PortalNoticia {

    private String id;
	private String name;
	private String url;
	private String parametrizacao; //json

	public PortalNoticia(String name, String url,String parametrizacao){
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.url = url;
		this.parametrizacao = parametrizacao;
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



