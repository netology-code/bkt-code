package ru.netology.ncraftmedia.crud.dto

enum class AttachmentType {
  IMAGE, AUDIO, VIDEO
}

data class AttachmentModel(val id: String, val url: String, val type: AttachmentType)

enum class PostType {
  POST, REPOST
}

data class PostModel(
  val id: Long,
  val source: PostModel? = null,
  val ownerId: Long,
  val ownerName: String,
  val created: Int,
  val content: String? = null,
  var likes: Int = 0,
  var likedByMe: Boolean = false,
  val reposts: Int = 0,
  val repostedByMe: Boolean = false,
  val link: String? = null,
  val type: PostType = PostType.POST,
  val attachment: AttachmentModel?
) {
  var likeActionPerforming = false
  fun updateLikes(updatedModel: PostModel) {
    if (id != updatedModel.id) throw IllegalAccessException("Ids are different")
    likes = updatedModel.likes
    likedByMe = updatedModel.likedByMe
  }
}
