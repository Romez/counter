(ns counter.app
  (:import
   [com.raylib Raylib Jaylib])
  (:gen-class))

(defn run []
  (Raylib/InitWindow 800 450 "Demo")
  (Raylib/SetTargetFPS 60)
  (while (not (Raylib/WindowShouldClose))
    (Raylib/BeginDrawing)

    (Raylib/ClearBackground Jaylib/RAYWHITE)
    (Raylib/DrawText "Congrats! You created your first window!", 190, 200, 20, Jaylib/LIGHTGRAY) ;

    (Raylib/EndDrawing))


  (Jaylib/CloseWindow))

(defn -main [& _args]
  (run)

  0)

(comment
  )
