import {definePreset} from '@primeng/themes';
import Material from '@primeng/themes/material';

/*
* 4 objets différents pour la customisation du thème PrimeNG Material Design
* → primitive
* → semantic
* → components : Personnalisation dans le composant directement
* → extends : Haut degré de personnalisation
*
 */
export const customTheme = definePreset(Material, {
  primitive: {
    borderRadius: {
      none: "0",
      xs: "4px",
      sm: "8px",
      md: "8px",
      lg: "8px",
      xl: "8px"
    },
  },
  semantic: {
    primary: {
      50: "#f8f7fc",
      100: "#dedaf1",
      200: "#c5bce6",
      300: "#ab9edb",
      400: "#9181d0",
      500: "#7763c5",
      600: "#6554a7",
      700: "#53458a",
      800: "#41366c",
      900: "#30284f",
      950: "#1e1931"
    },
    colorScheme: {
      surface: {
        600: '{accent.color}'
      }
    }

  },
  components: {
    button: {
      extend: {
        accent: {
          inverseColor: '#000'
        }
      },
      css: ({ dt }: { dt: (key: string) => string }) => `
        .p-button-accent {
            border-color: ${dt('accent.color')};
            color: ${dt('button.accent.inverseColor')};
        }
        `
      //Extend extérieur et un extend défini dans composant
    }
  },
  extend: {
    accent: {
      color: "#000000"
    }
  }
})
