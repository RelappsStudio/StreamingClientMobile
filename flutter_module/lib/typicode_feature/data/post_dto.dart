import 'package:json_annotation/json_annotation.dart';

import '../domain/post.dart';

part 'post_dto.g.dart';

@JsonSerializable()
class PostDTO {
  final int userId;
  final int id;
  final String title;
  final String body;

  PostDTO({required this.userId, required this.id, required this.title, required this.body});

  factory PostDTO.fromJson(Map<String, dynamic> json) => _$PostDTOFromJson(json);

  Map<String, dynamic> toJson() => _$PostDTOToJson(this);

  Post toDomain() {
    return Post(userId: userId, id: id, title: title, body: body);
  }
}