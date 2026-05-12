
import 'package:flutter_module/typicode_feature/domain/post.dart';

abstract class IPostRepository {

Future<List<Post>> getPosts();


}