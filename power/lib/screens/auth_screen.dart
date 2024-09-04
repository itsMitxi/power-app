import 'package:flutter/material.dart';
import 'package:power/constants/colors.dart';

class AuthScreen extends StatelessWidget {
  const AuthScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: AppColors.background,
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'Benvingut a Power',
              style: TextStyle(color: AppColors.onPrimary, fontSize: 24, fontWeight: FontWeight.bold),
            ),
            const Text(
              'Entrena de forma més intel·ligent.\nAconsegueix resultats.', 
              style: TextStyle(color: AppColors.onPrimaryDarker, fontSize: 20),
            ),
            const SizedBox(height: 20),
            Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                ElevatedButton(
                  onPressed: () {
                    Navigator.of(context).pushNamed('/register');
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: AppColors.background,
                    padding: const EdgeInsets.fromLTRB(10, 0, 10, 0),
                  ),
                  child: const Text(
                    'REGISTRAR-SE',
                    style: TextStyle(color: AppColors.primary, letterSpacing: 2)),
                ),
                const SizedBox(width: 10,),
                ElevatedButton(
                  onPressed: () {
                    Navigator.of(context).pushNamed('/login');
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: AppColors.primary,
                    padding: const EdgeInsets.fromLTRB(20, 0, 20, 0),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(5),
                    )
                  ),
                  child: const Text(
                    'INICIAR SESSIÓ',
                    style: TextStyle(color: AppColors.onPrimary, letterSpacing: 2)),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}