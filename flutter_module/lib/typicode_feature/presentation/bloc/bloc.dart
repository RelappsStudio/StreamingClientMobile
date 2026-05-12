

import 'package:bloc/bloc.dart';
import 'package:flutter_module/typicode_feature/domain/i_post_repository.dart';
import 'package:flutter_module/typicode_feature/presentation/bloc/events.dart';
import 'package:flutter_module/typicode_feature/presentation/bloc/typicode_state.dart';

class TypicodeBloc extends Bloc<TypicodeEvent, TypicodeState> {
  final IPostRepository _repository;

  TypicodeBloc(this._repository) : super(TypicodeLoading()) {
    on<OnGetPosts> (_onGetPosts);
  }

  Future<void> _onGetPosts(OnGetPosts event, Emitter<TypicodeState> emit) async {
    emit(TypicodeLoading());

    try {
      final posts = await _repository.getPosts();
      emit(TypicodeSuccess(posts));
    } catch (e) {
      emit(TypicodeError(e.toString()));
    }
  }
}