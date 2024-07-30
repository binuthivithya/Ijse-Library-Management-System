package edu.ijse.dto;

public class CategoryDto {
    private String catId;
    private String catName;
    private String catDesc;

    public CategoryDto() {
    }

    public CategoryDto(String catId, String catName, String catDesc) {
        this.catId = catId;
        this.catName = catName;
        this.catDesc = catDesc;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    @Override
    public String toString() {
        return "CategoryDto{" +
                "catId='" + catId + '\'' +
                ", catName='" + catName + '\'' +
                ", catDesc='" + catDesc +
                '}';
    }
}
