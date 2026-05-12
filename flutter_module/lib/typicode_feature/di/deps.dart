
import 'package:dio/dio.dart';
import 'package:flutter_module/typicode_feature/data/post_repository.dart';
import 'package:flutter_module/typicode_feature/domain/i_post_repository.dart';
import 'package:flutter_module/typicode_feature/presentation/bloc/bloc.dart';
import 'package:get_it/get_it.dart';

final container = GetIt.asNewInstance();

void registerDependencies() {
  container.registerLazySingleton(() => Dio());

  container.registerLazySingleton<IPostRepository>(() => PostRepository(container<Dio>()));

  container.registerFactory(() => TypicodeBloc(container<IPostRepository>()));
}