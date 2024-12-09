package Game;

public class MathB {
    public float areaTriangle(Vector2 point1, Vector2 point2, Vector2 point3) {
        return Math.abs(point1.x * (point2.y - point3.y) + point2.x * (point3.y - point1.y) + point3.x * (point1.y - point2.y)) / 2.0f;
    }

    public float distancePointPoint(Vector2 point1, Vector2 point2) {
        return Math.abs(point1.x - point2.x) + Math.abs(point1.y - point2.y);
    }

    public Vector2 vectorDistancePointPoint(Vector2 point1, Vector2 point2) {
        return new Vector2(Math.abs(point1.x - point2.x), Math.abs(point1.y - point2.y));
    }
}
