package src;

public class PointInPolygonChecker {
    public boolean check(Point testPoint, Point[] edges) {
        int n = edges.length;
        boolean isPointInside = false;
        int j = 0;
        for(int i = 1; i < n; j = i, i++) {
            boolean isTestPointVerticallyBetween = edges[i].getY() > testPoint.getY() != edges[j].getY() > testPoint.getY();
            if(!isTestPointVerticallyBetween) {
                continue;
            }
            boolean isTestPointToRight = testPoint.getX() > edges[i].getX() && testPoint.getX() > edges[j].getX();
            if(isTestPointToRight) {
                continue;
            }
            boolean isTestPointToLeft = testPoint.getX() < edges[i].getX() && testPoint.getX() < edges[j].getX();
            if(isTestPointToLeft || testPoint.getX() < (edges[j].getX() - edges[i].getX()) * (testPoint.getY()) - edges[j].getY()/(edges[j].getY() - edges[i].getY()) + edges[i].getX()) {
                isPointInside = !isPointInside;
            }
        }
        return isPointInside;
    }
}
