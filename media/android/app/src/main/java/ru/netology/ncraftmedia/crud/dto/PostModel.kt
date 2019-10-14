package ru.netology.ncraftmedia.crud.dto

import ru.netology.ncraftmedia.crud.BASE_URL

enum class AttachmentType {
  IMAGE, AUDIO, VIDEO
}

data class AttachmentModel(val id: String, val mediaType: AttachmentType) {
  val url
    get() = "$BASE_URL/api/v1/static/$id"
}

enum class PostType {
  POST, REPOST
}

data class PostModel(
  val id: Long,
  val source: PostModel? = null,
  val ownerId: Long,
  val ownerName: String,
  val created: Int,
  var content: String? = null,
  var likes: Int = 0,
  var likedByMe: Boolean = false,
  var reposts: Int = 0,
  var repostedByMe: Boolean = false,
  val link: String? = null,
  val type: PostType = PostType.POST,
  val attachment: AttachmentModel?
) {
  var likeActionPerforming = false
  fun updatePost(updatedModel: PostModel) {
    if (id != updatedModel.id)
      throw IllegalAccessException("Ids are different")
    likes = updatedModel.likes
    likedByMe = updatedModel.likedByMe
    content = updatedModel.content
    reposts = updatedModel.reposts
    repostedByMe = updatedModel.repostedByMe
  }
}
