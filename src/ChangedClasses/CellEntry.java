package ChangedClasses;// Add your documentation below:
import Interfaces.Index2D;
import UnchangedClasses.Ex2Utils;

public class CellEntry implements Index2D {

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public int getX() {return Ex2Utils.ERR;}

    @Override
    public int getY() {return Ex2Utils.ERR;}
}
