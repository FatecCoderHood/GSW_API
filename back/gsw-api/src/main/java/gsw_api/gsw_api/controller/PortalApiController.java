package gsw_api.gsw_api.controller;

import gsw_api.gsw_api.dto.DadosApi;
import gsw_api.gsw_api.model.Api;
import gsw_api.gsw_api.service.ApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apis")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @Operation(summary = "Create a new API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "API created successfully",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = Api.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<Api> create(@RequestBody DadosApi dadosApi) {
        Api api = apiService.create(dadosApi);
        return ResponseEntity.ok(api);
    }

    @Operation(summary = "Find API by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "API found",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = Api.class))),
        @ApiResponse(responseCode = "404", description = "API not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Api> findById(@PathVariable Long id) {
        return apiService.getApiById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get a list of all APIs")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of APIs retrieved",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = Api.class)))
    })
    @GetMapping
    public ResponseEntity<List<Api>> findAll() {
        List<Api> apis = apiService.getAllApis();
        return ResponseEntity.ok(apis);
    }

    @Operation(summary = "Update an existing API")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "API updated successfully",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = Api.class))),
        @ApiResponse(responseCode = "404", description = "API not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Api> update(@PathVariable Long id, @RequestBody DadosApi dadosApi) {
        Api updatedApi = apiService.updateApi(id, dadosApi);
        return updatedApi != null ? ResponseEntity.ok(updatedApi) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete an API by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "API deleted successfully"),
        @ApiResponse(responseCode = "404", description = "API not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        apiService.deleteApi(id);
        return ResponseEntity.noContent().build();
    }
}
