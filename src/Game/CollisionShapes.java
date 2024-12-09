package Game;

public class CollisionShapes {

    public CollisionShapes(){

    }

    public boolean checkCollisionRecs(Rectangle rec1,Rectangle rec2){

        if (rec1.x + rec1.width <= rec2.x || rec2.x + rec2.width <= rec1.x) {
            return false;
        }

        return rec1.y + rec1.height >= rec2.y && rec2.y + rec2.height >= rec1.y;
    }

    public boolean checkCollisionPointRec(Vector2 point,Rectangle rec){
        return rec.x <= point.x && point.x <= rec.x + rec.width && rec.y <= point.y && point.y <= rec.y + rec.height;
    }

    public boolean checkCollisionPointTriangle(Vector2 point, Triangle tri) {
        Vector2 p1 = tri.getVertex1();
        Vector2 p2 = tri.getVertex2();
        Vector2 p3 = tri.getVertex3();

        MathB m = new MathB();

        float areaOrig = m.areaTriangle(p1, p2, p3);

        float area1 = m.areaTriangle(point, p2, p3);
        float area2 = m.areaTriangle(p1, point, p3);
        float area3 = m.areaTriangle(p1, p2, point);

        float tolerance = 2.0f;

        return Math.abs(areaOrig - (area1 + area2 + area3)) <= tolerance;
    }

    public boolean checkCollisionPointLine(Vector2 point,Line line, float threshold){
        float px = point.x;
        float py = point.y;

        float x1 = line.start.x;
        float y1 = line.start.y;
        float x2 = line.end.x;
        float y2 = line.end.y;

        float numerator = Math.abs((y2 - y1) * px - (x2 - x1) * py + x2 * y1 - y2 * x1);
        float denominator = (float) Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));

        float distance = numerator / denominator;

        return distance <= threshold;
    }

}
