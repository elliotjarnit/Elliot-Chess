package dev.elliotjarnit.elliotchess;
import dev.elliotjarnit.elliotengine.Exceptions.NotTriangleException;
import dev.elliotjarnit.elliotengine.Graphics.EColor;
import dev.elliotjarnit.elliotengine.Handlers.FileHandler;
import dev.elliotjarnit.elliotengine.Handlers.ObjHandler;
import dev.elliotjarnit.elliotengine.Objects.EFace;
import dev.elliotjarnit.elliotengine.Objects.EObject;
import dev.elliotjarnit.elliotengine.Utils.Vector2;
import dev.elliotjarnit.elliotengine.Utils.Vector3;

import java.io.FileNotFoundException;

public class Skeleton extends EObject {
    public Skeleton(Vector3 origin) {
        super(origin);
        System.out.println("Skeleton");

        try {
            String[] data = FileHandler.loadFileFromResources("Skeleton.obj");
            EFace[] faces = ObjHandler.loadData(data);
            this.setFaces(faces);
            this.setColor(new EColor(240, 225, 166));
        } catch (NotTriangleException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        this.setRotationDegrees(new Vector2(0, this.getRotationDegrees().y + 2));
    }
}
