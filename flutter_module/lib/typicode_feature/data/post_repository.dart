

import 'package:dio/dio.dart';
import 'package:flutter_module/typicode_feature/data/post_dto.dart';
import 'package:flutter_module/typicode_feature/domain/i_post_repository.dart';
import 'package:flutter_module/typicode_feature/domain/post.dart';

class PostRepository implements IPostRepository {

  final Dio _dio;

  PostRepository(this._dio);

  @override
  Future<List<Post>> getPosts() async {
    final response = await _dio.get('https://jsonplaceholder.typicode.com/posts');

    final List data = response.data;

    return data.map((element) {
      final dto = PostDTO.fromJson(element);

      return dto.toDomain();
    }).toList();
  }
}