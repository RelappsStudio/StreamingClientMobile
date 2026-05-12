import 'package:flutter_module/typicode_feature/domain/post.dart';

sealed class TypicodeState {
  const TypicodeState();

}

class TypicodeLoading extends TypicodeState {
  const TypicodeLoading();
}

class TypicodeSuccess extends TypicodeState {
  final List<Post> posts;

  const TypicodeSuccess(this.posts);
}

class TypicodeError extends TypicodeState {
  final String message;

  const TypicodeError(this.message);
}