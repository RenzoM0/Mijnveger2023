package com.company;

public class Stapel<T> {
    class Vakje {
        private T inhoud;
        private Vakje vorige;

        public Vakje(T inhoud,Vakje vorige){
            this.inhoud = inhoud;
            this.vorige = vorige;
        }
        public Vakje getVorige(){return this.vorige;}
        public void setVorige(Vakje deze){this.vorige = deze;}
        public T getInhoud(){return inhoud;}
        public void setInhoud(T inhoud){this. inhoud = inhoud;}
    }

    private Vakje top;

    public Stapel(){

    }

    private boolean isLeeg(){
        return this.top == null;
    }
    public void push(T inhoud){
        Vakje v = new Vakje(inhoud,this.top);
        this.top = v;

    }
    public T pop(){
        T inhoudTop = null;
        if(!isLeeg()) {
            inhoudTop = this.top.getInhoud();
            this.top = this.top.getVorige();
            return inhoudTop;
        }
        return inhoudTop;
    }
    public int zoek(T deze) {
        if(!isLeeg()) {
            int locatie = 1;
            while (this.top.getVorige() != null) {
                if (this.top.getInhoud() == deze) {
                    return locatie;
                }
                this.top = this.top.getVorige();
                locatie++;
            }
        }
        return -1;
    }
    public String print(){
        if(!isLeeg()){
            StringBuilder str = new StringBuilder();
            while(this.top != null) {
                str.append(" ");
                str.append(this.top.getInhoud());
                this.top = this.top.getVorige();
            }
            return str.toString();
        }
        return "";
    }
    public Vakje getTop() {
        return this.top;
    }

    public void setTop(Vakje top) {
        this.top = top;
    }
}
