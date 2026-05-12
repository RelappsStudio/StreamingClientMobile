
import 'package:bloc_test/bloc_test.dart';
import 'package:flutter_module/typicode_feature/domain/post.dart';
import 'package:flutter_module/typicode_feature/presentation/bloc/bloc.dart';
import 'package:flutter_module/typicode_feature/presentation/bloc/events.dart';
import 'package:flutter_module/typicode_feature/presentation/bloc/typicode_state.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:mockito/mockito.dart';

import 'mocks/typicode_mocks.mocks.dart';

void main() {
  late MockIPostRepository mockIPostRepository;
  late TypicodeBloc bloc;

  setUp( () {
    mockIPostRepository = MockIPostRepository();
    bloc = TypicodeBloc(mockIPostRepository);
  });

  tearDown(() {
    bloc.close();
  });

  group("Typicode bloc tests", () {
    final testPosts = [
      Post(id: 1, title: 'test post', body: 'test body', userId: 1),
    ];

    blocTest<TypicodeBloc, TypicodeState>(
      'emits [Loading, Loaded] when data is fetched successfully',
      build: () {
        when(mockIPostRepository.getPosts()).thenAnswer((_) async => testPosts);
        return TypicodeBloc(mockIPostRepository);
      },
      act: (bloc) => bloc.add(OnGetPosts()),
      expect: () => [
        isA<TypicodeLoading>(),
        isA<TypicodeSuccess>().having((s) => s.posts, 'posts', testPosts),
      ],
      verify: (_) {
        verify(mockIPostRepository.getPosts()).called(1);
      },

    );

    blocTest<TypicodeBloc, TypicodeState>(
      'emits [DemoLoading, DemoError] when LoadPostsRequested fails',
      build: () {
        when(mockIPostRepository.getPosts()).thenThrow(Exception('API Error'));
        return bloc;
      },
      act: (bloc) => bloc.add(OnGetPosts()),
      expect: () => [
        isA<TypicodeLoading>(),
        isA<TypicodeError>().having((s) => s.message, 'message', contains('API Error')),
      ],
      verify: (_) {
        verify(mockIPostRepository.getPosts()).called(1);
      },
    );
  });
}