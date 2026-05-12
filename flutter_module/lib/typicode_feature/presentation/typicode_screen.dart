import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_module/typicode_feature/di/deps.dart';
import 'package:flutter_module/typicode_feature/presentation/bloc/events.dart';

import 'bloc/bloc.dart';
import 'bloc/typicode_state.dart';

class TypicodeScreen extends StatelessWidget {
  const TypicodeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
        create: (context) => container<TypicodeBloc>()..add(OnGetPosts()),
      child: Scaffold(
        appBar: AppBar(title: const Text("DI demo"),),
        body: BlocBuilder<TypicodeBloc, TypicodeState>(builder:(context, state) {
          switch(state) {

            case TypicodeLoading():
              return const Center(child: CircularProgressIndicator(),);
            case TypicodeSuccess():
              return ListView.separated(
                  itemBuilder: (context, index) =>
                      ListTile(
                        leading: Text(state.posts[index].id.toString()),
                        title: Text(state.posts[index].title),
                        subtitle: Text(state.posts[index].body),
                      ),
                separatorBuilder: (BuildContext context, int index) =>
                Container(
                  height: 2,
                  color: Colors.grey,
                ),
                itemCount: state.posts.length,
              );
            case TypicodeError():
              return Center(child: Text(state.message),);
          }
        },
        ),
      ),

    );
  }
}
