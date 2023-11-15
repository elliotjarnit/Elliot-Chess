package main.java.dev.elliotjarnit.ElliotChess;

import dev.elliotjarnit.ElliotEngine.Graphics.EColor;
import dev.elliotjarnit.ElliotEngine.Handlers.FileHandler;
import dev.elliotjarnit.ElliotEngine.Handlers.ObjHandler;
import dev.elliotjarnit.ElliotEngine.Objects.EFace;
import dev.elliotjarnit.ElliotEngine.Objects.EObject;
import dev.elliotjarnit.ElliotEngine.Utils.Vector2;
import dev.elliotjarnit.ElliotEngine.Utils.Vector3;

import java.io.FileNotFoundException;

public class Table extends EObject {
    public Table(Vector3 origin) {
        super(origin);

        String modelPath = "src/dev/elliotjarnit/ElliotChess/Models/Table.obj";
        try {
            String[] data = FileHandler.loadFile(modelPath);
            EFace[] faces = ObjHandler.loadData(data);
            this.setFaces(faces);
            this.setColor(new EColor(38, 19, 11));
        } catch (FileNotFoundException | ObjHandler.NotTriangleException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {

    }
}
