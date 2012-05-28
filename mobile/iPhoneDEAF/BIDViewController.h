//
//  BIDViewController.h
//  chat3
//
//  Created by developer on 12. 1. 5..
//  Copyright (c) 2012 HeadFlow All rights reserved.
//

#import <UIKit/UIKit.h>

@interface BIDViewController : UIViewController   {
    
}
@property (retain, nonatomic) IBOutlet UITextView *showView;

@property(nonatomic, strong) NSString *ipsend;
@property(nonatomic, strong) NSString *portsend;
@property(nonatomic, strong) NSString *tempstring;
- (IBAction)changeButtonPressed:(id)sender;

@end


