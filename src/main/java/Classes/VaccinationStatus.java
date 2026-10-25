package Classes;

public class VaccinationStatus {
    private final int polyclinicEmlangan;
    private final int polyclinicEmlanayotgan;
    private final int polyclinicEmlanmagan;
    private final int districtEmlangan;
    private final int districtEmlanayotgan;
    private final int districtEmlanmagan;
    private final int regionEmlangan;
    private final int regionEmlanayotgan;
    private final int regionEmlanmagan;

    private String polyclinicName;
    private String districtName;
    private String regionName;

    public VaccinationStatus(int polyclinicEmlangan, int polyclinicEmlanayotgan, int polyclinicEmlanmagan,
                             int districtEmlangan, int districtEmlanayotgan, int districtEmlanmagan,
                             int regionEmlangan, int regionEmlanayotgan, int regionEmlanmagan,
                             String polyclinicName, String districtName, String regionName) {
        this.polyclinicEmlangan = polyclinicEmlangan;
        this.polyclinicEmlanayotgan = polyclinicEmlanayotgan;
        this.polyclinicEmlanmagan = polyclinicEmlanmagan;
        this.districtEmlangan = districtEmlangan;
        this.districtEmlanayotgan = districtEmlanayotgan;
        this.districtEmlanmagan = districtEmlanmagan;
        this.regionEmlangan = regionEmlangan;
        this.regionEmlanayotgan = regionEmlanayotgan;
        this.regionEmlanmagan = regionEmlanmagan;
        this.polyclinicName = polyclinicName;
        this.districtName = districtName;
        this.regionName = regionName;
    }

    public int getPolyclinicEmlangan() { return polyclinicEmlangan; }
    public int getPolyclinicEmlanayotgan() { return polyclinicEmlanayotgan; }
    public int getPolyclinicEmlanmagan() { return polyclinicEmlanmagan; }
    public int getDistrictEmlangan() { return districtEmlangan; }
    public int getDistrictEmlanayotgan() { return districtEmlanayotgan; }
    public int getDistrictEmlanmagan() { return districtEmlanmagan; }
    public int getRegionEmlangan() { return regionEmlangan; }
    public int getRegionEmlanayotgan() { return regionEmlanayotgan; }
    public int getRegionEmlanmagan() { return regionEmlanmagan; }

    public String getPolyclinicName() { return polyclinicName; }
    public String getDistrictName() { return districtName; }
    public String getRegionName() { return regionName; }
}
