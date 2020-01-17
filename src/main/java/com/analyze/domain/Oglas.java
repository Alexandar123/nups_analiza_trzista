package com.analyze.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "oglas")
public class Oglas {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String image;
	private String image_link;
	private String kvadratura_T_L_Mesto;
	private String link_to_ad;
	private String offer_title;
	private String offer_title_link;
	private String datum_oglasa_puna_adresa;
	private String kratki_opis;
	private String opis;
	private String cena;
	private String cena_po_kvadratu;
	private String kvadratura;
	
	public Oglas() {}

	public Oglas(int id, String urlImage, String image_link, String kvadratura_T_L_Mesto, String linkToAd,
			String offerTitle, String offerTitleLink, String datumOglasa_punaAdresa, String kratki_opis, String opis,
			String cena, String cena_po_kvadratu, String kvadratura) {
		super();
		this.id = id;
		this.image = urlImage;
		this.image_link = image_link;
		this.kvadratura_T_L_Mesto = kvadratura_T_L_Mesto;
		this.link_to_ad = linkToAd;
		this.offer_title = offerTitle;
		this.offer_title_link = offerTitleLink;
		this.datum_oglasa_puna_adresa = datumOglasa_punaAdresa;
		this.kratki_opis = kratki_opis;
		this.opis = opis;
		this.cena = cena;
		this.cena_po_kvadratu = cena_po_kvadratu;
		this.kvadratura = kvadratura;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrlImage() {
		return image;
	}

	public void setUrlImage(String urlImage) {
		this.image = urlImage;
	}

	public String getImage_link() {
		return image_link;
	}

	public void setImage_link(String image_link) {
		this.image_link = image_link;
	}

	public String getKvadratura_T_L_Mesto() {
		return kvadratura_T_L_Mesto;
	}

	public void setKvadratura_T_L_Mesto(String kvadratura_T_L_Mesto) {
		this.kvadratura_T_L_Mesto = kvadratura_T_L_Mesto;
	}

	public String getLinkToAd() {
		return link_to_ad;
	}

	public void setLinkToAd(String linkToAd) {
		this.link_to_ad = linkToAd;
	}

	public String getOfferTitle() {
		return offer_title;
	}

	public void setOfferTitle(String offerTitle) {
		this.offer_title = offerTitle;
	}

	public String getOfferTitleLink() {
		return offer_title_link;
	}

	public void setOfferTitleLink(String offerTitleLink) {
		this.offer_title_link = offerTitleLink;
	}

	public String getDatumOglasa_punaAdresa() {
		return datum_oglasa_puna_adresa;
	}

	public void setDatumOglasa_punaAdresa(String datumOglasa_punaAdresa) {
		this.datum_oglasa_puna_adresa = datumOglasa_punaAdresa;
	}

	public String getKratki_opis() {
		return kratki_opis;
	}

	public void setKratki_opis(String kratki_opis) {
		this.kratki_opis = kratki_opis;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public String getCena() {
		return cena;
	}

	public void setCena(String cena) {
		this.cena = cena;
	}

	public String getCena_po_kvadratu() {
		return cena_po_kvadratu;
	}

	public void setCena_po_kvadratu(String cena_po_kvadratu) {
		this.cena_po_kvadratu = cena_po_kvadratu;
	}

	public String getKvadratura() {
		return kvadratura;
	}

	public void setKvadratura(String kvadratura) {
		this.kvadratura = kvadratura;
	}

	@Override
	public String toString() {
		return "Oglas [id=" + id + ", urlImage=" + image + ", image_link=" + image_link + ", kvadratura_T_L_Mesto="
				+ kvadratura_T_L_Mesto + ", linkToAd=" + link_to_ad + ", offerTitle=" + offer_title + ", offerTitleLink="
				+ offer_title_link + ", datumOglasa_punaAdresa=" + datum_oglasa_puna_adresa + ", kratki_opis=" + kratki_opis
				+ ", opis=" + opis + ", cena=" + cena + ", cenaPoKvadratu=" + cena_po_kvadratu + ", kvadratura="
				+ kvadratura + "]";
	}
	
}