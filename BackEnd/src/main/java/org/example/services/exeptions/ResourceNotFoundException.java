package org.example.services.exeptions;

/* Quando tentamos buscar por um codigo que não existe usuario pelo metedo finbyid em
 * usuarioService, será gerado um erro. E nesse camada de serviço vamos buscar esse erro
 * para depois instanciar em outro metodo na camara resource.
 */
public class ResourceNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(Object id) {
        super("Resource nott found. Id " + id);
    }

}