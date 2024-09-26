package gsw_api.gsw_api.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gsw_api.gsw_api.model.PortalNoticia;
import gsw_api.gsw_api.service.PortalNoticiaService;

@RestController
@CrossOrigin
@RequestMapping("/portais")
public class PortalNoticiaController {
    private PortalNoticiaService portalNoticiaService = new PortalNoticiaService();

    @GetMapping()
	Iterable<PortalNoticia> getPortais() {
		return portalNoticiaService.getPortais();
	}

	@PostMapping()
	public PortalNoticia addNoticia(@RequestBody PortalNoticia portalNoticia) {
		return portalNoticiaService.addPortalNoticia(portalNoticia);
	}

	@GetMapping("/{id}")
    public PortalNoticia getPortalNoticiaById(@PathVariable String id) {
        return portalNoticiaService.getPortalNoticiaById(id);
    }
}
