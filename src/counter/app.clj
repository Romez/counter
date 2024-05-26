(ns counter.app
  (:import
   [com.raylib Raylib Jaylib])
  (:gen-class))

(def state (atom {:number 0}))

(defn rects-coords
  [nums]
  (->> nums
       (map (fn [i n]
              
              (->> (range n)
                   (map (fn [j]
                          #_[i (range n)]
                          {:x i, :y j}
                          {:x (- 800 (* (inc i) 42))
                           :y (- 600 (* (inc j) 42))
                           :val (inc j)}))))
            (range))
       (flatten))
  )

(defn- split-number
  [num]
  (if (zero? num)
    []
    (conj (split-number (/ (- num (rem num 10)) 10)) (rem num 10))))

(comment
  (->> (split-number 12)
       (rects-coords)
       )
  (rects-coords [1 2])
  )

(defn run []
  (Raylib/InitWindow 800 600 "Counter")
  (Raylib/SetTargetFPS 60)
  (while (not (Raylib/WindowShouldClose))
    (Raylib/BeginDrawing)

    (Raylib/ClearBackground Jaylib/RAYWHITE)

    (when (Raylib/IsKeyPressed Jaylib/KEY_UP)
      (swap! state (fn [{:keys [number]}] {:number (inc number)})))

    (when (Raylib/IsKeyPressed Jaylib/KEY_DOWN)
      (swap! state (fn [{:keys [number]}] {:number (dec number)})))
    
    (run! (fn [{:keys [x y val]}]
            (Raylib/DrawRectangle (- x 20), (- y 20), 40, 40, Jaylib/MAROON)
            (Raylib/DrawText (str val), (- x 5), (- y 10), 30, Jaylib/LIGHTGRAY))
          (->> (reverse (split-number (:number @state)))
               (rects-coords)))
    
    #_(Raylib/DrawText "Congrats! You created your first window!", 190, 200, 20, Jaylib/LIGHTGRAY) ;

    (Raylib/EndDrawing))


  (Jaylib/CloseWindow))

(defn -main [& _args]
  (run)

  0)

(comment
  "1")
