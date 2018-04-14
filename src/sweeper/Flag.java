package sweeper;

class Flag {
    private Matrix flagMap;
    private int countOfClosedBoxes;

    void start () {
        flagMap = new Matrix(Box.CLOSED);
        countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
    }

    Box get (Coord coord) {
        return flagMap.get(coord);

    }

    void toggleFlagedToBox(Coord coord) {
        switch (flagMap.get(coord)) {
            case FLAGED: setClosedToBox (coord); break;
            case CLOSED: setFlagedToBox (coord); break;

        }
    }

    void setOpenedToBox(Coord coord) {
        flagMap.set(coord,Box.OPENED);
        countOfClosedBoxes--;
    }

    private void setFlagedToBox(Coord coord) {
        
        flagMap.set(coord,Box.FLAGED);
    }



    private void setClosedToBox(Coord coord) {
        flagMap.set(coord,Box.CLOSED);
    }

    int getCountOfClosedBoxes() {
        return countOfClosedBoxes;

    }

    void setBombedToBox(Coord coord) {
        flagMap.set(coord, Box.BOMBED);
    }

     void setOpenedToClosedBombBox(Coord coord) {
        if (flagMap.get(coord) == Box.CLOSED)
            flagMap.set(coord, Box.OPENED);
    }

     void setNobombToFlagedSafeBox(Coord coord) {
         if (flagMap.get(coord) == Box.FLAGED)
             flagMap.set(coord, Box.NOBOMB);
     }

     int getCountofFlagedBoxesAround(Coord coord) {
        int count = 0;
        for (Coord aroound : Ranges.getCoordsAround(coord))
            if (flagMap.get(aroound) == Box.FLAGED)
                count++;
        return count;
    }
}
