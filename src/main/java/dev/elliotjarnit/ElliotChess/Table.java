package dev.elliotjarnit.ElliotChess;
import dev.elliotjarnit.elliotengine.Graphics.EColor;
import dev.elliotjarnit.elliotengine.Handlers.FileHandler;
import dev.elliotjarnit.elliotengine.Handlers.ObjHandler;
import dev.elliotjarnit.elliotengine.Objects.EFace;
import dev.elliotjarnit.elliotengine.Objects.EObject;
import dev.elliotjarnit.elliotengine.Utils.Vector2;
import dev.elliotjarnit.elliotengine.Utils.Vector3;

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
