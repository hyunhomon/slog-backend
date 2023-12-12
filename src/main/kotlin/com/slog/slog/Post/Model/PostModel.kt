package com.slog.slog.Post.Model

import lombok.Getter
import lombok.Setter

@Setter
@Getter
class PostModel(
    var title : String = "",
    var thumbnail : String = "",
    var category : List<String> = listOf(),
    var views : Int = 0,
    var likes : Int = 0,
    var dislikes : Int = 0
)