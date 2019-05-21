/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package refleksja.v2;

/**
 *
 * @author pawel
 */
public class Song {
    private String autor;
    private String tytul;
    private String album;
    private String tekst;
    
    public Song()
        {
        this.autor="noname";
        this.tytul="brak";
        this.album=null;
        this.tekst=null;
        }
    
    public Integer setAutor(String autor)
        {
        if(autor.length()>0)
            {
            this.autor=autor;   
            return 0;
            }
        return 1;
        }
    public String getAutor()
        {
        if(this.autor==null) return "null";    
        return this.autor;
        }
    
   public Integer setAlbum(String autor)
        {
        if(autor.length()>0)
            {    
            this.autor=autor;
            return 0;
            }
        return 1;
        }
    public String getAlbum()
        {
        if(this.autor==null) return "null";    
        return this.autor;
        } 
    
    public Integer setTytul(String tytul)
        {
        if(tytul.length()>0)
            {
            this.tytul=tytul;
            return 0;
            }
        return 1;
        }
    public String getTytul()
        {
            if(this.tytul==null) return "null";
            return this.tytul;
        }
    
    public Integer setTekst(String tekst)
        {
        if(tekst.length()>0)
            {
            this.tekst=tekst;
            return 0;
            }
        return 1;
        }
    public String getTekst()
            {
            if(this.tekst==null) return "null";
            return this.tekst;
            }
}
