package soa.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import soa.entities.Categorie;
import soa.entities.Produit;
import soa.metier.ProduitMetierImpl;
import soa.repository.CategorieRepository;
import soa.repository.ProduitRepository;

@RestController // pour déclarer un service web de type REST
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/produits")  //    http://localhost:8080/produits
public class ProduitRESTController {
    @Autowired // pour l'injection de dépendances
    private ProduitRepository produitRepos;
    @Autowired
    ProduitMetierImpl produitMetier;
    @Autowired
    CategorieRepository categorieRepository;

    //  Message d'accueil
    //  http://localhost:8080/produits/index  (GET)
    @GetMapping(value ="/index" )
    public String accueil() {
        return "BienVenue au service Web REST 'produits'.....";
    }

    //  Afficher la liste des produits
    //  http://localhost:8080/produits/ (GET)

    @GetMapping(
            // spécifier le path de la méthode
            value= "/",
            // spécifier le format de retour en XML
            produces = {  MediaType.APPLICATION_JSON_VALUE }
    )
    public  List<Produit> getAllProduits() {
        return produitRepos.findAll();

    }

    //  Afficher un produit en spécifiant son 'id'
    //  http://localhost:8080/produits/{id} (GET)
    @GetMapping(
            // spécifier le path de la méthode qui englobe un paramètre
            value= "/{id}" ,
            // spécifier le format de retour en XML
            produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public Produit getProduit(@PathVariable Long id) {
        Produit p =produitRepos.findById(id).get();
        return p;
    }

    // Supprimer un produit par 'id' avec la méthode 'GET'
    //  http://localhost:8080/produits/delete/{id}  (GET)
    @DeleteMapping(
            // spécifier le path de la méthode
            value = "/delete/{id}")
    public void deleteProduit(@PathVariable Long id)
    {
        produitRepos.deleteById(id);
    }

    //  ajouter un produit avec la méthode "POST"
    //  http://localhost:8080/produits/   (POST)
    @GetMapping(value = "/categories",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Categorie> getAllCat() {
        return categorieRepository.findAll();
    }
    @PostMapping(
            value = "/add",
            consumes = { MediaType.APPLICATION_JSON_VALUE }
    )
    public Produit saveProduit(@RequestBody Produit p) {
        System.out.println("Received Product: " + p.toString());

        try {
            return produitRepos.save(p);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    //  modifier un produit avec la méthode "PUT"
    //  http://localhost:8080/produits/   (PUT)
    @PutMapping(
            // spécifier le path de la méthode
            value = "/update"  ,
            //spécifier le format de retour
            consumes =  MediaType.APPLICATION_JSON_VALUE
    )

    public Produit updateProduit(@RequestBody Produit p)
    {
        System.out.println(" produit recuperer de angular"+p+"  "+p.getId());
        Optional<Produit> prd=produitRepos.findById(p.getId());
        System.out.println("produit trouver a la base"+prd+"  "+p.getId());
        if(prd.isPresent()){
            // System.out.println(prd+"  "+id);
            prd.get().setCode(p.getCode());
            prd.get().setDesignation(p.getDesignation());
            prd.get().setPrix(p.getPrix());


            // System.out.println(prd+"  "+id);


            return produitRepos.save(prd.get());
        }else return null;

    }


    // Supprimer un produit  avec la méthode 'DELETE'
    //  http://localhost:8080/produits/   (DELETE)
   /* @DeleteMapping(value = "/delete",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean deleteProduit(@RequestParam Long p) {
        System.out.println(p);

try{
        Optional<Produit> prd = produitRepos.findById(p);
if (prd.isPresent()) {
    produitRepos.delete(prd.get());
    return true;

} }catch (Error error){
    System.out.println(error);
}
    return false;}
*/
}
