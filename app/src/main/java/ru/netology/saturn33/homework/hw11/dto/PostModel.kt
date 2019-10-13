package ru.netology.saturn33.homework.hw11.dto

enum class PostType {
    POST, REPOST,//, EVENT, YOUTUBE, AD
    FOOTER
}

class PostModel(
    val id: Long,
    val created: Long,
    val author: UserResponseDto,
    val source: PostModel? = null,//for repost
    var content: String? = null,
    var likes: Int = 0,
    var likedByMe: Boolean = false,
    var reposts: Int = 0,
    var repostedByMe: Boolean = false,
//    var shares: Int = 0,
//    var sharedByMe: Boolean = false,
//    val link: String? = null,//for ad
    val postType: PostType = PostType.POST,
    val attachment: AttachmentModel? = null
)
{
    var likeActionPerforming = false
    var repostActionPerforming = false
    fun updatePost(updatedModel: PostModel) {
        if (id != updatedModel.id) throw IllegalAccessException("Ids are different")
        likes = updatedModel.likes
        likedByMe = updatedModel.likedByMe
        content = updatedModel.content
        reposts = updatedModel.reposts
        repostedByMe = updatedModel.repostedByMe
    }
}
