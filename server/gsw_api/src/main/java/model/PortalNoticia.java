package model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
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

}



