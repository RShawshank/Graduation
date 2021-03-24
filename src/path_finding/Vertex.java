package path_finding;

public class Vertex {
        int id;//vertex id
        int x;
        int y;
        long Hdistance;//the distantion from the vertex to  target
        long Gdistance;//the distantion from the vertex to  source
        long Fdistance;//Fdistance=Hdistance+Gdistance
        boolean isBelong;//check is the vertex is belong to the path
        Vertex path_father;
        public Vertex(){

        }
        public Vertex(int x,int y,int id)
        {
            this.id=id;
            this.x=x;
            this.y=y;
            this.isBelong=true;
            this.Hdistance=Long.MAX_VALUE;
            this.Gdistance=Long.MAX_VALUE;
            this.Fdistance=Long.MAX_VALUE;
        }
    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isBelong() {
        return isBelong;
    }

    public long getFdistance() {
        return Fdistance;
    }

    public long getGdistance() {
        return Gdistance;
    }

    public long getHdistance() {
        return Hdistance;
    }


    public void setBelong(boolean belong) {
        isBelong = belong;
    }


    public void setFdistance(long fdistance) {
        Fdistance = fdistance;
    }

    public void setGdistance(long gdistance) {
        Gdistance = gdistance;
    }

    public void setHdistance(long hdistance) {
        Hdistance = hdistance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}

