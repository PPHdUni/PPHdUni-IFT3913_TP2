public class Metrique {

    public String path;
    public int NLOC,
                NOCom,
                WMC;
    public float DCP;

    public Metrique(String path, int ncloc, float dcp, int nocom, int wmc) {
        this.path = path;
        this.NLOC = ncloc;
        this.DCP = dcp;
        this.NOCom = nocom;
        this.WMC = wmc;
    }

}
